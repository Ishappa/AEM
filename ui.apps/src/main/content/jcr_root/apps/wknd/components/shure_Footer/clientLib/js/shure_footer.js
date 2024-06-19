  

function validateForm() {
        var emailInput = document.getElementById("emailInput").value;
        var errorMessage = document.getElementById("errorMessage");
        let vaildEmail=document.querySelector(".valid_email");
		let form =document.querySelector("#subscribeForm");
    
        form.addEventListener("submit",(e)=>{
    	e.preventDefault();
	});
        if (emailInput.trim() === "") {

           errorMessage.style.display = "block";
           
            errorMessage.style.color = "red";
            errorMessage.style.fontSize = "12.6px";
            errorMessage.style.margin = "0px";
            vaildEmail.textContent='';
        } else {

            errorMessage.style.display = "none";
         vaildEmail.textContent="Email submitted: "+emailInput;
       
           
            console.log("Email submitted: " + emailInput);
            
        
            
        }
    
    }
