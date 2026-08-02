const API_BASE = window.API_BASE ?? '';

async function apiRequest(path, options = {}) {
  const isFormData = options.body instanceof FormData;
  const response = await fetch(`${API_BASE}${path}`, {
    credentials: 'include',
    headers: {
      ...(isFormData ? {} : { 'Content-Type': 'application/json' }),
      ...(options.headers ?? {}),
    },
    ...options,
  });

  let body = null;
  const contentType = response.headers.get('content-type') ?? '';
  if (contentType.includes('application/json')) {
    body = await response.json();
  }

  return { response, body };
}

export async function login(email, password) {
  return apiRequest('/api/v1/auth/login', {
    method: 'POST',
    body: JSON.stringify({ email, password }),
  });
}

export async function register({ email, password, name, familyId }) {
  return apiRequest('/api/v1/auth/register', {
    method: 'POST',
    body: JSON.stringify({ email, password, name, familyId }),
  });
}

export async function checkFamily(familyId) {
  return apiRequest(`/api/v1/families/${encodeURIComponent(familyId)}`, {
    method: 'GET',
  });
}

export async function fetchSession() {
  const { response, body } = await apiRequest('/api/v1/auth/me', { method: 'GET' });
  if (response.ok) return body;
  return null;
}

export async function logout() {
  return apiRequest('/api/v1/auth/logout', { method: 'POST' });
}

export async function uploadMenuPhoto(file) {
  const formData = new FormData();
  formData.append('file', file);
  return apiRequest('/api/v1/menus/photos', {
    method: 'POST',
    body: formData,
  });
}

export async function createMenu({ name, description, photoId }) {
  return apiRequest('/api/v1/menus', {
    method: 'POST',
    body: JSON.stringify({ name, description, photoId }),
  });
}

export async function updateMenu(menuId, { name, description, photoId }) {
  return apiRequest(`/api/v1/menus/${encodeURIComponent(menuId)}`, {
    method: 'PUT',
    body: JSON.stringify({ name, description, photoId }),
  });
}

export async function deleteMenu(menuId) {
  return apiRequest(`/api/v1/menus/${encodeURIComponent(menuId)}`, {
    method: 'DELETE',
  });
}

export async function fetchMyMenuBoard() {
  return apiRequest('/api/v1/menus', { method: 'GET' });
}

export async function fetchPublicMenuBoard(shareSlug) {
  return apiRequest(`/api/v1/menu-boards/${encodeURIComponent(shareSlug)}`, {
    method: 'GET',
  });
}

export function showMessage(element, text, type) {
  element.textContent = text;
  element.className = `form-message ${type}`;
  element.hidden = !text;
}
