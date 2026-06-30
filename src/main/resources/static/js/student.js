window.onload = () => {
    initHome(renderSearch);
};

/* ПОШУК (тільки студент) */
function renderSearch(){
    const container = document.getElementById("searchContainer");

    if(container){
        container.innerHTML = `
            <input type="text" placeholder="Пошук викладача / консультації...">
        `;
    }
}