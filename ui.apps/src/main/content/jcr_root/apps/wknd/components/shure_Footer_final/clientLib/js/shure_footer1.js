
  function validateForm() {
        var emailInput = document.getElementById("emailInput").value;
        var errorMessage = document.getElementById("errorMessage");
        var validEmailSpan = document.querySelector(".valid_email");
        var form = document.querySelector("#subscribeForm");
        var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        form.addEventListener("submit", (e) => {
            e.preventDefault();
        });

        if (emailInput.trim() === "" || !emailRegex.test(emailInput)) {
            errorMessage.style.display = "block";
            errorMessage.style.color = "red";
            errorMessage.style.fontSize = "12.6px";
            errorMessage.style.margin = "0px";
            validEmailSpan.textContent = '';
        } else {
            errorMessage.style.display = "none";
            validEmailSpan.style.display = "flex";
            validEmailSpan.textContent = "Subscribed Successfully!";
            console.log("Email submitted: " + emailInput);
        }

        setTimeout(() => {
            validEmailSpan.textContent = "";
            validEmailSpan.style.display = "none";
        }, 3000);
    }


