const params = new URLSearchParams(window.location.search);

const role = params.get("role");

const title = document.getElementById("title");

if(role==="teacher"){

    title.innerText="Авторизація викладача";

}else{

    title.innerText="Авторизація студента";

}

document
.getElementById("registerButton")
.addEventListener("click",()=>{

    location.href="register.html?role="+role;

});

document.getElementById("loginButton").addEventListener("click", async () => {

    const params = new URLSearchParams(window.location.search);
    const role = params.get("role");

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const response = await fetch(`${API_URL}/auth/login/${role}`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            email,
            password,
            role
        })
    });

    if (!response.ok) {
        alert("Невірний логін або пароль");
        return;
    }

    const data = await response.json();

    // 💾 збереження користувача
    localStorage.setItem("token", data.token);
    localStorage.setItem("userId", data.id);
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