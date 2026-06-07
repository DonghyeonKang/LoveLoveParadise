import { fetchSession, logout } from './api.js';

const USER_ICON_SVG = `
  <svg class="user-icon__svg" viewBox="0 0 24 24" aria-hidden="true">
    <path d="M12 12c2.761 0 5-2.239 5-5s-2.239-5-5-5-5 2.239-5 5 2.239 5 5 5zm0 2c-3.866 0-7 2.239-7 5v1h14v-1c0-2.761-3.134-5-7-5z"/>
  </svg>
`;

function renderGuestNav(links, activePage) {
  links.innerHTML = `
    <a href="login.html" class="site-nav__link${activePage === 'login' ? ' site-nav__link--active' : ''}">로그인</a>
    <a href="register.html" class="site-nav__link${activePage === 'register' ? ' site-nav__link--active' : ''}">회원가입</a>
  `;
}

function renderUserNav(links, session) {
  links.replaceChildren();

  const user = document.createElement('div');
  user.className = 'site-nav__user';
  user.title = session.name;

  const icon = document.createElement('span');
  icon.className = 'user-icon';
  icon.setAttribute('aria-label', `${session.name}님으로 로그인됨`);
  icon.innerHTML = USER_ICON_SVG;

  const name = document.createElement('span');
  name.className = 'user-icon__name';
  name.textContent = session.name;

  const logoutBtn = document.createElement('button');
  logoutBtn.type = 'button';
  logoutBtn.className = 'site-nav__logout-btn';
  logoutBtn.textContent = '로그아웃';
  logoutBtn.addEventListener('click', handleLogout);

  user.append(icon, name, logoutBtn);
  links.append(user);
}

async function handleLogout() {
  try {
    await logout();
  } catch {
    // 쿠키 삭제는 서버 응답 기준이지만, 네트워크 오류 시에도 로그인 페이지로 이동
  }
  window.location.href = 'login.html';
}

export function renderNav(session, activePage = null) {
  const links = document.getElementById('site-nav-links');
  if (!links) return;

  if (session) {
    renderUserNav(links, session);
  } else {
    renderGuestNav(links, activePage);
  }
}

export async function initNav({ requireAuth = false, redirectTo = 'login.html', activePage = null } = {}) {
  const session = await fetchSession();

  if (requireAuth && !session) {
    window.location.href = redirectTo;
    return null;
  }

  if (!requireAuth && session && (activePage === 'login' || activePage === 'register')) {
    window.location.href = 'index.html';
    return session;
  }

  renderNav(session, activePage);
  return session;
}
