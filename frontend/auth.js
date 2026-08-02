import { fetchSession, logout, fetchMyMenuBoard } from './api.js';

const USER_ICON_SVG = `
  <svg class="user-icon__svg" viewBox="0 0 24 24" aria-hidden="true">
    <path d="M12 12c2.761 0 5-2.239 5-5s-2.239-5-5-5-5 2.239-5 5 2.239 5 5 5zm0 2c-3.866 0-7 2.239-7 5v1h14v-1c0-2.761-3.134-5-7-5z"/>
  </svg>
`;

function renderGuestNav(links, activePage) {
  links.innerHTML = `
    <a href="/login" class="site-nav__link${activePage === 'login' ? ' site-nav__link--active' : ''}">로그인</a>
    <a href="/register" class="site-nav__link${activePage === 'register' ? ' site-nav__link--active' : ''}">회원가입</a>
  `;
}

function renderUserNav(links, session) {
  links.replaceChildren();

  const user = document.createElement('div');
  user.className = 'site-nav__user';

  const trigger = document.createElement('button');
  trigger.type = 'button';
  trigger.className = 'user-menu-trigger';
  trigger.title = session.name;
  trigger.setAttribute('aria-haspopup', 'true');
  trigger.setAttribute('aria-expanded', 'false');

  const icon = document.createElement('span');
  icon.className = 'user-icon';
  icon.setAttribute('aria-label', `${session.name}님으로 로그인됨`);
  icon.innerHTML = USER_ICON_SVG;

  const name = document.createElement('span');
  name.className = 'user-icon__name';
  name.textContent = session.name;

  trigger.append(icon, name);

  const dropdown = document.createElement('div');
  dropdown.className = 'user-menu-dropdown';
  dropdown.hidden = true;

  const closeDropdown = () => {
    dropdown.hidden = true;
    trigger.setAttribute('aria-expanded', 'false');
  };

  const shareBtn = document.createElement('button');
  shareBtn.type = 'button';
  shareBtn.className = 'user-menu-dropdown__item';
  shareBtn.textContent = '공유하기';
  shareBtn.addEventListener('click', () => {
    handleShare(shareBtn, closeDropdown);
  });

  const familyLink = document.createElement('a');
  familyLink.href = '/family-manage';
  familyLink.className = 'user-menu-dropdown__item';
  familyLink.textContent = '가족관리';

  const menuLink = document.createElement('a');
  menuLink.href = '/menu-manage';
  menuLink.className = 'user-menu-dropdown__item';
  menuLink.textContent = '메뉴관리';

  const accountLink = document.createElement('a');
  accountLink.href = '/account';
  accountLink.className = 'user-menu-dropdown__item';
  accountLink.textContent = '계정';

  const logoutBtn = document.createElement('button');
  logoutBtn.type = 'button';
  logoutBtn.className = 'user-menu-dropdown__item user-menu-dropdown__item--danger';
  logoutBtn.textContent = '로그아웃';
  logoutBtn.addEventListener('click', handleLogout);

  dropdown.append(shareBtn, familyLink, menuLink, accountLink, logoutBtn);

  trigger.addEventListener('click', (event) => {
    event.stopPropagation();
    if (dropdown.hidden) {
      dropdown.hidden = false;
      trigger.setAttribute('aria-expanded', 'true');
    } else {
      closeDropdown();
    }
  });

  document.addEventListener('click', (event) => {
    if (!user.contains(event.target)) {
      closeDropdown();
    }
  });

  user.append(trigger, dropdown);
  links.append(user);
}

async function handleShare(triggerBtn, onDone) {
  const originalText = triggerBtn.textContent;

  try {
    const { response, body } = await fetchMyMenuBoard();
    if (!response.ok) {
      throw new Error('failed to load share link');
    }

    const shareUrl = `${window.location.origin}/menu-board?slug=${body.shareSlug}`;
    await navigator.clipboard.writeText(shareUrl);
    triggerBtn.textContent = '복사됨!';
  } catch {
    triggerBtn.textContent = '복사 실패';
  } finally {
    setTimeout(() => {
      triggerBtn.textContent = originalText;
      onDone();
    }, 1500);
  }
}

async function handleLogout() {
  try {
    await logout();
  } catch {
    // 쿠키 삭제는 서버 응답 기준이지만, 네트워크 오류 시에도 로그인 페이지로 이동
  }
  window.location.href = '/login';
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

export async function initNav({ requireAuth = false, redirectTo = '/login', activePage = null } = {}) {
  const session = await fetchSession();

  if (requireAuth && !session) {
    window.location.href = redirectTo;
    return null;
  }

  if (!requireAuth && session && (activePage === 'login' || activePage === 'register')) {
    window.location.href = '/';
    return session;
  }

  renderNav(session, activePage);
  return session;
}
