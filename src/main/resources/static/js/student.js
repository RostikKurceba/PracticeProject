window.onload = () => {
    initHome(renderSearch);
};

function renderSearch() {

    const container =
        document.getElementById("searchContainer");

    container.innerHTML = `
        <input
            type="text"
            id="subjectInput"
            placeholder="Пошук викладача / консультації...">

        <button id="searchButton">
            Знайти
        </button>
    `;

    document
        .getElementById("searchButton")
        .addEventListener("click", searchTeachers);
}

async function searchTeachers() {

    const subject =
        document
            .getElementById("subjectInput")
            .value
            .trim();

    if (subject === "") {
        alert("Введіть предмет");
        return;
    }

    const response =
        await fetch(
            `${API_URL}/teachers/search?subject=${encodeURIComponent(subject)}`
        );

    if (!response.ok) {
        alert("Помилка пошуку");
        return;
    }

    const teachers = await response.json();

    const result =
        document.getElementById("searchResult");

    result.innerHTML = "";

    if (teachers.length === 0) {
        result.innerHTML = `
            <div class="not-found">
                Викладачів не знайдено
            </div>
        `;
        return;
    }

    teachers.forEach(teacher => {

        result.innerHTML += `
            <button class="teacher-card"
                    onclick="openTeacher(${teacher.id})">

                <div class="teacher-name">
                    ${teacher.lastName} ${teacher.initials}
                </div>

                <div class="teacher-subject">
                    ${teacher.subject}
                </div>

            </button>
        `;
    });
}

function openTeacher(id) {
    location.href = "teacher-profile.html?id=" + id;
}