const days = ["Пн","Вт","Ср","Чт","Пт"];
const hours = [
    "08:00","09:00","10:00","11:00","12:00",
    "13:00","14:00","15:00","16:00","17:00","18:00"
];

let teacherId = null;
let studentId = null;

window.onload = async () => {

    const params = new URLSearchParams(window.location.search);
    teacherId = params.get("id");

    studentId = localStorage.getItem("userId");

    await loadTeacherInfo();
    await loadCalendar();

};

function goBack(){
    history.back();
}

/* =========================
   TEACHER INFO
========================= */
async function loadTeacherInfo(){

    const el = document.getElementById("teacherInfo");

    el.innerText = "Викладач ID: " + teacherId;
}

/* =========================
   CALENDAR
========================= */
async function loadCalendar(){

    const response =
        await fetch(`${API_URL}/appointments/teacher/${teacherId}`);

    const data = await response.json();

    renderCalendar(data);
}

function renderCalendar(data){

    const table = document.getElementById("calendar");

    let html = "<tr><th></th>";

    days.forEach(d => html += `<th>${d}</th>`);
    html += "</tr>";

    hours.forEach(hour => {

        html += `<tr><td>${hour}</td>`;

        for(let i=0;i<5;i++){

            const day = days[i];

            const found = data.find(a =>
                a.consultationDay === day &&
                a.consultationTime === hour
            );

            if(found){

                html += `<td class="booked">■</td>`;

            } else {

                html += `
                    <td class="free"
                        onclick="book('${day}','${hour}')">
                        □
                    </td>`;
            }
        }

        html += "</tr>";
    });

    table.innerHTML = html;
}

/* =========================
   BOOK APPOINTMENT
========================= */
async function book(day, time){

    if(!studentId){
        alert("Спочатку увійдіть як студент");
        return;
    }

    const response =
        await fetch(`${API_URL}/appointments/book`, {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: new URLSearchParams({
                teacherId,
                studentId,
                day,
                time
            })
        });

    if(!response.ok){
        alert("Помилка запису");
        return;
    }

    alert("Успішно записано!");

    await loadCalendar();
}