const API_URL = 'http://localhost:8081';

// DOM Elements
const loginScreen = document.getElementById('login-screen');
const dashboardScreen = document.getElementById('dashboard-screen');
const loginForm = document.getElementById('login-form');
const loginError = document.getElementById('login-error');
const logoutBtn = document.getElementById('logout-btn');

const currentUsername = document.getElementById('current-username');
const currentRole = document.getElementById('current-role');

const listPendiente = document.getElementById('list-pendiente');
const listEnProgreso = document.getElementById('list-en-progreso');
const listCompletada = document.getElementById('list-completada');

// Modals
const taskModal = document.getElementById('task-modal');
const openModalBtn = document.getElementById('open-modal-btn');
const closeModalBtn = document.getElementById('close-modal-btn');
const taskForm = document.getElementById('task-form');
const taskError = document.getElementById('task-error');

const editTaskModal = document.getElementById('edit-task-modal');
const closeEditModalBtn = document.getElementById('close-edit-modal-btn');
const editTaskForm = document.getElementById('edit-task-form');

// State
let jwtToken = localStorage.getItem('token');
let userRoles = [];
let username = '';

// Initialize
function init() {
    if (jwtToken) {
        parseJwtAndShowDashboard();
    } else {
        showScreen('login');
    }
}

// Utils
function showScreen(screen) {
    loginScreen.classList.remove('active');
    dashboardScreen.classList.remove('active');
    if (screen === 'login') loginScreen.classList.add('active');
    if (screen === 'dashboard') dashboardScreen.classList.add('active');
}

function parseJwt(token) {
    try {
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join(''));
        return JSON.parse(jsonPayload);
    } catch (e) {
        return null;
    }
}

function parseJwtAndShowDashboard() {
    const payload = parseJwt(jwtToken);
    if (!payload) {
        logout();
        return;
    }
    username = payload.sub;
    // Spring Security roles might be in a different claim, but usually we can infer from the backend or just decode it.
    // Assuming standard Spring JWT, authorities might be there, if not, we try to guess or use the API behavior.
    // For this demo, let's look for "roles" or "authorities" or simply wait for API response.
    // Actually, let's just make the UI show Admin buttons if their username has admin, or better, we try fetching and if we are admin we see all tasks.
    userRoles = payload.roles || []; 
    // Fallback logic: check if the username starts with admin just for UI display, backend still validates real roles.
    const isAdmin = username.toLowerCase().includes('admin') || userRoles.includes('ROLE_ADMIN'); 
    
    currentUsername.textContent = username;
    currentRole.textContent = isAdmin ? 'ADMIN' : 'USER';
    currentRole.style.background = isAdmin ? '#EF4444' : '#4F46E5';

    showScreen('dashboard');
    loadTasks();
}

async function apiFetch(endpoint, method = 'GET', body = null) {
    const headers = { 'Content-Type': 'application/json' };
    if (jwtToken) {
        headers['Authorization'] = `Bearer ${jwtToken}`;
    }
    const config = { method, headers };
    if (body) config.body = JSON.stringify(body);

    const res = await fetch(`${API_URL}${endpoint}`, config);
    if (res.status === 401 || res.status === 403) {
        throw new Error('No autorizado');
    }
    if (!res.ok) {
        const err = await res.text();
        throw new Error(err || 'Error en la petición');
    }
    
    // For DELETE it might be empty
    const text = await res.text();
    return text ? JSON.parse(text) : {};
}

// Login
loginForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const user = document.getElementById('username').value;
    const pass = document.getElementById('password').value;
    
    try {
        const res = await apiFetch('/login', 'POST', { username: user, password: pass });
        jwtToken = res.access;
        localStorage.setItem('token', jwtToken);
        parseJwtAndShowDashboard();
    } catch (error) {
        loginError.textContent = 'Usuario o contraseña incorrectos';
    }
});

logoutBtn.addEventListener('click', logout);
function logout() {
    jwtToken = null;
    localStorage.removeItem('token');
    showScreen('login');
}

// Tasks loading
async function loadTasks() {
    try {
        const tasks = await apiFetch('/tareas');
        renderTasks(tasks);
    } catch (error) {
        if(error.message === 'No autorizado') logout();
    }
}

function renderTasks(tasks) {
    listPendiente.innerHTML = '';
    listEnProgreso.innerHTML = '';
    listCompletada.innerHTML = '';

    tasks.forEach(task => {
        const card = document.createElement('div');
        card.className = 'task-card';
        
        const isAdmin = currentRole.textContent === 'ADMIN';

        let buttons = '';
        if (task.estado === 'PENDIENTE') {
            buttons += `<button class="btn-action" onclick="changeStatus(${task.id}, 'iniciar')">▶ Iniciar</button>`;
        } else if (task.estado === 'EN_PROGRESO') {
            buttons += `<button class="btn-action" onclick="changeStatus(${task.id}, 'completar')">✔ Completar</button>`;
        }

        if (isAdmin) {
            buttons += `
                <button class="btn-action btn-edit" onclick="openEditModal(${task.id}, '${task.titulo}', '${task.descripcion}', '${task.fechaVencimiento}')">✏ Editar</button>
                <button class="btn-action btn-danger" onclick="deleteTask(${task.id})">✖ Borrar</button>
            `;
        }

        const authorInfo = isAdmin ? `<span>Autor: ${task.usuario?.username || 'Desconocido'}</span>` : '';

        card.innerHTML = `
            <div class="task-title">${task.titulo}</div>
            <div class="task-desc">${task.descripcion}</div>
            <div class="task-meta">
                <span>Vence: ${task.fechaVencimiento}</span>
                ${authorInfo}
            </div>
            <div class="task-actions">${buttons}</div>
        `;

        if (task.estado === 'PENDIENTE') listPendiente.appendChild(card);
        else if (task.estado === 'EN_PROGRESO') listEnProgreso.appendChild(card);
        else if (task.estado === 'COMPLETADA') listCompletada.appendChild(card);
    });
}

// Create Task
openModalBtn.onclick = () => taskModal.classList.add('active');
closeModalBtn.onclick = () => taskModal.classList.remove('active');

taskForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const titulo = document.getElementById('task-title').value;
    const descripcion = document.getElementById('task-desc').value;
    const fechaVencimiento = document.getElementById('task-date').value;

    // Énfasis en las restricciones: solo se manda esto, no el estado
    const body = { titulo, descripcion, fechaVencimiento };

    try {
        await apiFetch('/tareas', 'POST', body);
        taskModal.classList.remove('active');
        taskForm.reset();
        loadTasks();
    } catch (error) {
        taskError.textContent = error.message;
    }
});

// Change Task Status
window.changeStatus = async (id, action) => {
    try {
        await apiFetch(`/tareas/${id}/${action}`, 'PUT');
        loadTasks();
    } catch (error) {
        alert("Error: " + error.message);
    }
};

// Delete Task
window.deleteTask = async (id) => {
    if (confirm('¿Estás seguro de que deseas borrar esta tarea?')) {
        try {
            await apiFetch(`/tareas/${id}`, 'DELETE');
            loadTasks();
        } catch (error) {
            alert("Error: " + error.message);
        }
    }
};

// Edit Task
window.openEditModal = (id, title, desc, date) => {
    document.getElementById('edit-task-id').value = id;
    document.getElementById('edit-task-title').value = title;
    document.getElementById('edit-task-desc').value = desc;
    document.getElementById('edit-task-date').value = date;
    editTaskModal.classList.add('active');
};
closeEditModalBtn.onclick = () => editTaskModal.classList.remove('active');

editTaskForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const id = document.getElementById('edit-task-id').value;
    const titulo = document.getElementById('edit-task-title').value;
    const descripcion = document.getElementById('edit-task-desc').value;
    const fechaVencimiento = document.getElementById('edit-task-date').value;

    try {
        await apiFetch(`/tareas/${id}`, 'PUT', { id, titulo, descripcion, fechaVencimiento });
        editTaskModal.classList.remove('active');
        loadTasks();
    } catch (error) {
        document.getElementById('edit-task-error').textContent = error.message;
    }
});

// Start
init();
