let weekOffset = 0;

const days = ["Пн", "Вт", "Ср", "Чт", "Пт"];

const hours = [
    "8:00",
    "9:00",
    "10:00",
    "11:00",
    "12:00",
    "13:00",
    "14:00",
    "15:00",
    "16:00",
    "17:00",
    "18:00"
];

let appointments = [];

async function initHome(extraInit){

    renderUser();

    renderWeek();

    if(typeof extraInit === "function"){
        extraInit();
    }

    await loadAppointments();

    updateButtons();

}

function renderUser(){

    const lastName =
        localStorage.getItem("lastName") || "User";

    const first =
        localStorage.getItem("firstName")?.[0] || "";

    const middle =
        localStorage.getItem("middleName")?.[0] || "";

    const el =
        document.getElementById("userLabel");

    if(el){

        el.innerText =
            `${lastName} ${first}.${middle}.`;

    }

}

function getMonday(date){

    const d = new Date(date);

    const day = d.getDay();

    const diff =
        d.getDate() - (day === 0 ? 6 : day - 1);

    return new Date(d.setDate(diff));

}

function renderWeek(){

    const today = new Date();

    let base = new Date();

    if(today.getDay() === 6 || today.getDay() === 0){

        base.setDate(
            base.getDate() + (8 - today.getDay())
        );

    }

    const monday = getMonday(base);

    monday.setDate(
        monday.getDate() + weekOffset * 7
    );

    const friday = new Date(monday);

    friday.setDate(friday.getDate() + 4);

    const format =
        d => d.getDate().toString().padStart(2,"0");

    const label =

        `${format(monday)} - ${format(friday)}
        ${monday.toLocaleString("uk",{month:"long"})}
        ${monday.getFullYear()}`;

    document.getElementById("weekLabel").innerText =
        label;

}

function changeWeek(dir){

    if(weekOffset + dir < 0){
        return;
    }

    weekOffset += dir;

    renderWeek();

    renderCalendar();

    updateButtons();

}

function updateButtons(){

    document.getElementById("prevWeek").disabled =
        weekOffset === 0;

}

async function loadAppointments(){

    appointments = [];

    const userId =
        localStorage.getItem("userId");

    const role =
        localStorage.getItem("role");

    let url = "";

    if(role === "teacher"){

        url =
            `${API_URL}/appointments/teacher/${userId}`;

    }
    else{

        url =
            `${API_URL}/appointments/student/${userId}`;

    }

    const response =
        await fetch(url);

    if(!response.ok){

        renderCalendar();

        return;

    }

    appointments =
        await response.json();

    renderCalendar();

}

function renderCalendar(){

    const table =
        document.getElementById("calendar");

    if(!table){
        return;
    }

    let html = "<tr><th></th>";

    days.forEach(day => {

        html += `<th>${day}</th>`;

    });

    html += "</tr>";

    hours.forEach(hour=>{

        html += `<tr>`;

        html += `<td>${hour}</td>`;

        days.forEach(day=>{

            const appointment =
                appointments.find(a=>

                    a.consultationDay===day &&

                    a.consultationTime===hour

                );

            if(appointment){

                const role =
                    localStorage.getItem("role");

                let text = "";

                if(role==="teacher"){

                    text =
                        appointment.studentName || "";

                }
                else{

                    text =
                        appointment.teacherName || "";

                }

                html += `

                    <td class="busy">

                        ${text}

                    </td>

                `;

            }
            else{

                html += "<td></td>";

            }

        });

        html += "</tr>";

    });

    table.innerHTML = html;

}

function logout(){

    localStorage.clear();

    location.href =
        "../index.html";

}