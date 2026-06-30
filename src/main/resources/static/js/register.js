const params = new URLSearchParams(window.location.search);

const role = params.get("role");

const title = document.getElementById("title");

const teacherField = document.getElementById("teacherField");

if(role==="teacher"){

    title.innerHTML="Реєстрація викладача";

    teacherField.innerHTML=`
        <input
            type="text"
            id="subject"
            placeholder="Предмет">
    `;

}else{

    title.innerHTML="Реєстрація студента";

}

document.querySelector("button").addEventListener("click", async () => {

    const params = new URLSearchParams(window.location.search);
    const role = params.get("role");

    const lastName = document.getElementById("lastName").value;
    const firstName = document.getElementById("firstName").value;
    const middleName = document.getElementById("middleName").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    let subject = null;

    if (role === "teacher") {
        subject = document.getElementById("subject").value;
    }

    const response = await fetch(`${API_URL}/auth/register`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            lastName,
            firstName,
            middleName,
            email,
            password,
            role,
            subject
        })
    });

    if (!response.ok) {
        alert("Помилка реєстрації");
        return;
    }

    const data = await response.json();

    // 💾 зберігаємо як після логіну
    localStorage.setItem("token", data.token);
    localStorage.setItem("role", data.role);
    localStorage.setItem("lastName", data.lastName);
    localStorage.setItem("firstName", data.firstName);
    localStorage.setItem("middleName", data.middleName);

    // 🚀 РЕДІРЕКТ
    if (data.role === "student") {
        location.href = "student-home.html";
    } else {
        location.href = "teacher-home.html";
    }
});