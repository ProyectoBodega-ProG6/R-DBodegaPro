const botones = document.querySelectorAll('.btn-icon[data-panel]');
const paneles = document.querySelectorAll('.panel');
const perfilBtn = document.getElementById('btn-perfil');
const panelPerfil = document.getElementById('panel-perfil');

botones.forEach(boton => {
  boton.addEventListener('click', () => {
    const panelId = boton.getAttribute('data-panel');
    const panel = document.getElementById(panelId);
    const estaActivo = panel && panel.classList.contains('active');

    paneles.forEach(p => p.classList.remove('active'));
    botones.forEach(b => b.classList.remove('active'));
    panelPerfil.classList.remove('active');
    perfilBtn.classList.remove('active');

    if (panel && !estaActivo) {
      panel.classList.add('active');
      boton.classList.add('active');
    }

    // 🔹 cargar fragmento thymeleaf si tiene url
    const url = boton.dataset.url;
    if (panelId && url) {
        fetch(url)
            .then(res => res.text())
            .then(html => {
                const tempDiv = document.createElement('div');
                tempDiv.innerHTML = html;

                // Buscar el fragmento thymeleaf (contenido)
                const fragment = tempDiv.querySelector('[th\\:fragment="contenido"], [data-th-fragment="contenido"]');

                if (fragment) {
                    document.getElementById(panelId).querySelector('.contenido').innerHTML = fragment.innerHTML;
                } else {
                    document.getElementById(panelId).querySelector('.contenido').innerHTML = html;
                }
            })
            .catch(err => console.error('Error cargando panel:', err));
    }
  });
});

perfilBtn.addEventListener('click', () => {
  const activo = panelPerfil.classList.contains('active');
  paneles.forEach(p => p.classList.remove('active'));
  botones.forEach(b => b.classList.remove('active'));

  if (!activo) {
    panelPerfil.classList.add('active');
    perfilBtn.classList.add('active');
  } else {
    panelPerfil.classList.remove('active');
    perfilBtn.classList.remove('active');
  }
});
