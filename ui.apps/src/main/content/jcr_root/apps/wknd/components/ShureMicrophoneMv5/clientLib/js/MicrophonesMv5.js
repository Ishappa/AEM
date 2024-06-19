const formatter = new Intl.NumberFormat('en-IN', {
                    style: 'currency',
                    currency: 'INR'
                });
                


function fetchDataFromServlet() {
    var servletUrl = '/bin/shuredataMv5';
    var xhr = new XMLHttpRequest();
    xhr.open('GET', servletUrl, true);
    xhr.responseType = 'json';
    xhr.onload = function () {
        if (xhr.status === 200) {
            var responseData = xhr.response;
            console.log(responseData);
       
            var products = responseData.data.products.items;
            var variants = responseData.data.products.items.variants;
            
            var total_counts = responseData.data.products;
           
           	
            var mv5Container = document.getElementById('color_mv5');
            
            
            
              mv5Container.innerHTML = '';

            products.forEach((product) => {
               
                var productHTML = `
                   
                  
                `;
                mv5Container.innerHTML += productHTML;

               
                product.variants.forEach((variant) => {
                    var variantHTML = `
                        
                         <p>${product.name}</p>
                         <p>${product.sku}</p>
                        <div class="image_container">
                            <img src="${variant.product.thumbnail.url}" alt="${variant.product.thumbnail.label}">
                        </div> 
                        <p>${variant.product.short_description.html}</p>
                    `;
                    mv5Container.innerHTML += variantHTML;
                });
                
            });
            
    
        } else {
            console.error('Request failed. Status: ' + xhr.status);
        }
    };
    xhr.onerror = function () {
        console.error('Request failed');
    };
    xhr.send();
}

fetchDataFromServlet();
console.log('Request failed');


