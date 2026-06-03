const API_BASE = window.API_BASE ?? '';

async function apiRequest(path, options = {}) {
  const response = await fetch(`${API_BASE}${path}`, {
    credentials: 'include',
    headers: {
      'Content-Type': 'application/json',
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

export function showMessage(element, text, type) {
  element.textContent = text;
  element.className = `form-message ${type}`;
  element.hidden = !text;
}
