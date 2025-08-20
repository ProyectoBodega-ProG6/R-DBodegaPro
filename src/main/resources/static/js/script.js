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

document.querySelectorAll('.btn-icon').forEach(btn => {
    btn.addEventListener('click', function () {
        const panelId = this.dataset.panel;
        const url = this.dataset.url;

        if(panelId && url) {
            fetch(url)
                .then(res => res.text())
                .then(html => {
                    document.getElementById(panelId).querySelector('.contenido').innerHTML = html;
                })
                .catch(err => console.error('Error cargando panel:', err));
        }
    });
});