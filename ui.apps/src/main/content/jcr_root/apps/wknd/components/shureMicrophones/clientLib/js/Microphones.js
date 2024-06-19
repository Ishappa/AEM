  const formatter = new Intl.NumberFormat('en-IN', {
                    style: 'currency',
                    currency: 'INR'
                });
                


function fetchDataFromServlet() {
    var servletUrl = '/bin/shuregraphql';
    var xhr = new XMLHttpRequest();
    xhr.open('GET', servletUrl, true);
    xhr.responseType = 'json';
    xhr.onload = function () {
        if (xhr.status === 200) {
            var responseData = xhr.response;
            console.log(responseData);
            var products = responseData.data.products.items;
            
            var total_counts = responseData.data.products;
            var shure_sort = document.getElementById('shure_sort');
            const sortDiv = document.createElement("div");
            sortDiv.classList.add("items_count");
            sortDiv.innerHTML=`<p>${total_counts.total_count} items found</p>`;
            shure_sort.appendChild(sortDiv);
           
            var productsContainer = document.getElementById('products_Details');
            products.forEach((product,index) => {
                
                const productDiv = document.createElement("div");
                var status = product.stock_status;
                 
                productDiv.classList.add("product");
                
                
              
                const normalPrices = formatter.format(product.price.minimalPrice.amount.value);
                const specialPrices = formatter.format(product.price.regularPrice.amount.value);

                
                
                 
         if(status != "IN_STOCK"){
                       	
                /*productDiv.style.opacity = "0.3"; */
                
                productDiv.classList.add("products_items_outof_stock")
                productDiv.innerHTML = `
                
                	<div class="products_items " >
                    	<div class="products_items_img">
                        <div class="image_container">
                        <img src="${product.thumbnail.url}" alt="${product.thumbnail.label}"> </div>
                        
                        <div class="product_content">
                        <p class="price">${normalPrices}</p>
                        <p class="special_price">${specialPrices}</p>
                         <p class="p_name">${product.name}</p> 
                         </div>
                         </div>
                           <div class="productCard_Action">
                             <div class="product_Add_Remove">
                                <div class="input_item_quantity">
                                   <input type="number" value="1" min=1 max=10  />
                                    <button type="button" aria-label="decrement" class="btn btn_decrement" disabled>
                                      <span>-</span>
                                    </button>

                                    <button type="button" aria-label="increment" class="btn btn_increment">
                                      <span>+</span>
                                    </button>
                                  </div>
                             </div>

                       	 		<button class="add_to_cart">ADD TO CART</button>
         				  </div>
                         
                     
                    </div>
                 
                    <h6 class = "item_status" >OUT OF STOCK</h6>
                `;
                            
                            
              }else{
              
                      productDiv.innerHTML = `
                	<div class="products_items">
                    <div class="products_items_img">
                    
                        <figure class="image_container">
                        <img src="${product.thumbnail.url}" alt="${product.thumbnail.label}">
                        </figure>
                      
                        <div class="product_content">
                        <p class="price">${normalPrices}</p>
                        <p class="special_price">${specialPrices}</p>
                        <p class="p_name">${product.name}</p>  
                        <h6></h6>
                        </div>
                        
                    </div>
                        <div class="productCard_Action">
                                 <div class="product_Add_Remove">
                                    <div class="input_item_quantity">
                                       <input type="number" value="1" min=1 max=10 />
                                        <button type="button" aria-label="decrement" class="btn btn_decrement" onclick="decrementProductQun(event,${index})" disabled >
                                          <span>-</span>
                                        </button>

                                        <button type="button" aria-label="increment" class="btn btn_increment" onclick="incrementProductQun(event,${index})">
                                          <span>+</span>
                                        </button>
                                      </div>
                                 </div>

                       	 		<button class="add_to_cart">ADD TO CART</button>
         				   </div>  
 
                   </div>
                `;
              
              }
                productsContainer.appendChild(productDiv);
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

function incrementProductQun(event,index){
let inputBoxs=document.querySelectorAll("[type=number]")
    let inputValue;
    inputValue=parseInt(inputBoxs[index].value);
    inputValue++;
    if(inputValue>1){
        document.querySelectorAll(".btn_decrement")[index].removeAttribute("disabled");
    }
    inputBoxs[index].value=inputValue;
}

function decrementProductQun(event,index){
let inputBoxs=document.querySelectorAll("[type=number]")


    let inputValue;
    inputValue=parseInt(inputBoxs[index].value);
    if(inputValue>1)
    inputValue--;
    if(inputValue === 1){
        document.querySelectorAll(".btn_decrement")[index].setAttribute("disabled","");
       // document.querySelectorAll(".btn_decrement")[index].style.cursor = "not-allowed";
    }

    inputBoxs[index].value=inputValue;
    


}

