document.getElementById("current-page").textContent = `${document.title}`;

document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("#rentForm1");
    if (!form) {
        return;
    }

    form.addEventListener("submit", function (event) {
        const userConfirmed = confirm("OK?");
        if (!userConfirmed) {
            event.preventDefault();
        }
    });
});

document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("#rentForm2");
    if (!form) {
        return;
    }

    form.addEventListener("submit", function (event) {
        const userConfirmed = confirm("OK?");
        if (!userConfirmed) {
            event.preventDefault();
        }
    });
});

