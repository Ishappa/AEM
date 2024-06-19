
let mobileMenuBtnOpen = document.querySelector(".mobile_menu_btn");
let headerBottom = document.querySelector(".header_bottom");
let mobileMenuCloseBtn = document.querySelector(".mobile_menu_close_btn");
let mobileExpandBtn = document.querySelectorAll(".mobile_expand_btn");
let productMenu = document.querySelectorAll(".nav_prod_menu");
console.log(productMenu);
function toggleVisibility() {
  var div = document.querySelector(".myDiv");
  div.classList.toggle("hidden");
}



mobileMenuBtnOpen.addEventListener("click", () => {
  headerBottom.classList.toggle("sidebar_open");
});

mobileMenuCloseBtn.addEventListener("click", () => {
  headerBottom.classList.remove("sidebar_open");
});

mobileExpandBtn.forEach((expanBtn, index) => {
  if (index < 3) {
    expanBtn.addEventListener("click", () => {
        /*
         var secondDiv = document.getElementById("secondDiv");
        if (secondDiv.style.display === "none") {
            secondDiv.style.display = "block";
        } else {
            secondDiv.style.display = "none";
        }
        */
       expanBtn.textContent=expanBtn.textContent === "+" ? "View All - " : "+";
        openAccordion(productMenu[index], index);
    });
  }
  else if(index == 4){
       expanBtn.addEventListener("click", () => {
       expanBtn.textContent=expanBtn.textContent === "+" ? "View All - " : "+";
        openAccordion(productMenu[index - 1], index);
    });
  }
});

function openAccordion(element,index) {
  element.style.maxHeight = element.style.maxHeight? null: element.scrollHeight + "px";
    
}






  function toggleCart() {
        var cartDetails = document.getElementById("cartDetails");

        if (cartDetails.style.display === "none") {
            cartDetails.style.display = "block";
        } else {
            cartDetails.style.display = "none";
        }
    }

    function viewCart() {
        alert("Viewing Cart");
    }

    function securityCheck() {
        alert("Security Checkup");
    }

