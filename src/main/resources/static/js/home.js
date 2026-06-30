let weekOffset = 0;

const days = ["Пн","Вт","Ср","Чт","Пт"];
const hours = [
    "8:00","9:00","10:00","11:00","12:00",
    "13:00","14:00","15:00","16:00","17:00","18:00"
];

/* INIT (спільний старт) */
function initHome(extraInit){

    renderUser();

    renderWeek();

    renderCalendar();

    updateButtons();

    if(typeof extraInit==="function"){
        extraInit();
    }

}

/* USER NAME */
function renderUser(){
    const lastName = localStorage.getItem("lastName") || "User";
    const first = localStorage.getItem("firstName")?.[0] || "";
    const middle = localStorage.getItem("middleName")?.[0] || "";

    const el = document.getElementById("userLabel");
    if (el) {
        el.innerText = `${lastName} ${first}.${middle}.`;
    }
}

/* WEEK LOGIC */
function getMonday(date){
    const d = new Date(date);
    const day = d.getDay();
    const diff = d.getDate() - (day === 0 ? 6 : day - 1);
    return new Date(d.setDate(diff));
}

function renderWeek(){
    const today = new Date();

    let base = new Date();
    if(today.getDay() === 6 || today.getDay() === 0){
        base.setDate(base.getDate() + (8 - today.getDay()));
    }

    const monday = getMonday(base);
    monday.setDate(monday.getDate() + weekOffset * 7);

    const friday = new Date(monday);
    friday.setDate(friday.getDate() + 4);

    const format = (d)=> d.getDate().toString().padStart(2,"0");

    const label =
        `${format(monday)} - ${format(friday)} ${monday.toLocaleString('uk',{month:'long'})} ${monday.getFullYear()}`;

    const el = document.getElementById("weekLabel");
    if (el) el.innerText = label;
}

/* WEEK NAV */
function changeWeek(dir){

    if (weekOffset + dir < 0) return;

    weekOffset += dir;

    renderWeek();

    renderCalendar();

    updateButtons();

}

function updateButtons(){
    const btn = document.getElementById("prevWeek");
    if (btn) btn.disabled = weekOffset === 0;
}

/* LOGOUT */
function logout(){
    localStorage.clear();
    location.href = "../index.html";
}

/* CALENDAR */
function renderCalendar(){
    const table = document.getElementById("calendar");
    if (!table) return;

    let html = "<tr><th></th>";

    days.forEach(d => html += `<th>${d}</th>`);
    html += "</tr>";

    hours.forEach(h => {
        html += `<tr><td>${h}</td>`;
        for(let i=0;i<5;i++){
            html += `<td></td>`;
        }
        html += "</tr>";
    });

    table.innerHTML = html;
}