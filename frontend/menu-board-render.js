function createMenuCard(item) {
  const card = document.createElement('div');
  card.className = 'menu-card';

  const imgWrapper = document.createElement('div');
  imgWrapper.className = 'img-wrapper';

  if (item.photoId) {
    const img = document.createElement('img');
    img.className = 'menu-img';
    img.src = `/api/v1/photos/${encodeURIComponent(item.photoId)}/thumb.jpg`;
    img.alt = item.name;
    imgWrapper.appendChild(img);
  }

  const name = document.createElement('div');
  name.className = 'menu-name';
  name.textContent = item.name;

  const detail = document.createElement('p');
  detail.className = 'menu-detail';
  detail.textContent = item.description;

  card.append(imgWrapper, name, detail);
  return card;
}

export function renderMenuGrid(container, items) {
  container.querySelectorAll('.menu-container').forEach((el) => el.remove());

  const section = document.createElement('div');
  section.className = 'menu-container';

  const header = document.createElement('h3');
  header.className = 'menu-container-header';
  header.textContent = '메뉴';
  section.appendChild(header);

  for (let i = 0; i < items.length; i += 3) {
    const wrapper = document.createElement('div');
    wrapper.className = 'menu-card-wrapper';
    items.slice(i, i + 3).forEach((item) => wrapper.appendChild(createMenuCard(item)));
    section.appendChild(wrapper);
  }

  container.appendChild(section);
}
