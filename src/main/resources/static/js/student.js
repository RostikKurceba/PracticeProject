window.onload = () => {
    initHome(renderSearch);
};

function renderSearch(){

    const container =
        document.getElementById("searchContainer");

    container.innerHTML = `

        <input
            id="subjectInput"
            type="text"
            placeholder="Пошук викладача / консультації...">

        <button id="searchButton">
            Знайти
        </button>

    `;

    document
        .getElementById("searchButton")
        .onclick = searchTeachers;

}

async function searchTeachers(){

    const subject =
        document
            .getElementById("subjectInput")
            .value
            .trim();

    if(subject===""){

        alert("Введіть предмет");

        return;

    }

    const response =
        await fetch(
            `${API_URL}/teachers/search?subject=${encodeURIComponent(subject)}`
        );

    if(!response.ok){

        alert("Помилка пошуку");

        return;

    }

    const teachers = await response.json();

    const result =
        document.getElementById("searchResult");

    result.innerHTML = "";

    if(teachers.length===0){

        result.innerHTML =

            "<p>Викладачів не знайдено.</p>";

        return;

    }

    teachers.forEach(teacher=>{

        result.innerHTML += `

            <button
                class="teacher-card"
                onclick="openTeacher(${teacher.id})">

                <strong>

                    ${teacher.lastName}
                    ${teacher.initials}

                </strong>

                <br>

                ${teacher.subject}

            </button>

        `;

    });

}

function openTeacher(id) {
    location.href = "teacher-profile.html?id=" + id;
}