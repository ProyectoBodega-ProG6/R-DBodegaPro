document.addEventListener("DOMContentLoaded", () => {
  const botones = document.querySelectorAll(".btn-panel");
  const paneles = document.querySelectorAll(".panel");

  botones.forEach((btn) => {
    btn.addEventListener("click", () => {
      const panelId = btn.getAttribute("data-panel");

      paneles.forEach((panel) => {
        panel.classList.remove("active");
      });

      const panelActivo = document.getElementById(panelId);
      if (panelActivo) {
        panelActivo.classList.add("active");
      }
    });
  });
});
