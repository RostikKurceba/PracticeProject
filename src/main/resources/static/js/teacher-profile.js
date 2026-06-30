const days = ["Пн","Вт","Ср","Чт","Пт"];

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

let teacherId;
let studentId;

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

/* ===========================
   ВИКЛАДАЧ
=========================== */

async function loadTeacherInfo(){

    const response =
        await fetch(`${API_URL}/teachers/${teacherId}`);

    if(!response.ok){

        document.getElementById("teacherInfo").innerText =
            "Викладач";

        return;

    }

    const teacher = await response.json();

    document.getElementById("teacherInfo").innerText =
        `${teacher.lastName} ${teacher.firstName} ${teacher.middleName} • ${teacher.subject}`;

}

/* ===========================
   КАЛЕНДАР
=========================== */

async function loadCalendar(){

    const response =
        await fetch(`${API_URL}/appointments/teacher/${teacherId}`);

    const appointments = await response.json();

    renderCalendar(appointments);

}

function renderCalendar(appointments){

    const table = document.getElementById("calendar");

    let html = "<tr><th></th>";

    days.forEach(day=>{

        html += `<th>${day}</th>`;

    });

    html += "</tr>";

    hours.forEach(hour=>{

        html += `<tr>`;

        html += `<td>${hour}</td>`;

        for(let i=0;i<5;i++){

            const day = days[i];

            const booked = appointments.find(a=>

                a.consultationDay===day &&

                a.consultationTime===hour

            );

            if(booked){

                html += `
                    <td class="booked">
                        ■
                    </td>
                `;

            }else{

                html += `
                    <td
                        class="free"
                        onclick="book('${day}','${hour}')">

                        □

                    </td>
                `;

            }

        }

        html += "</tr>";

    });

    table.innerHTML = html;

}

/* ===========================
   ЗАПИС
=========================== */

async function book(day,time){

    if(studentId==null){

        alert("Спочатку увійдіть як студент");

        return;

    }

    const response =
        await fetch(`${API_URL}/appointments/book`,{

            method:"POST",

            headers:{

                "Content-Type":"application/x-www-form-urlencoded"

            },

            body:new URLSearchParams({

                teacherId,

                studentId,

                day,

                time

            })

        });

    if(!response.ok){

        const text = await response.text();

        alert(text);

        return;

    }

    alert("Ви успішно записались!");

    loadCalendar();

}