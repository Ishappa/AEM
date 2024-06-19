
  const formatter = new Intl.NumberFormat('en-IN', {
                    style: 'currency',
                    currency: 'INR'
                });

function displayProducts(responseData) {
    var products = responseData.data.products.items;
    var productsContainer = document.getElementById('products_Details');
    // Clear existing products
    productsContainer.innerHTML = '';
    var total_count = document.getElementById('totalCount');
    total_count.innerHTML=`
    			<p>${responseData.data.products.total_count} items found</p>
    `
    products.forEach((product,index) => {
        const productDiv = document.createElement("div");
        productDiv.classList.add("product");
        var status = product.stock_status;
        

        const normalPrices = formatter.format(product.price.minimalPrice.amount.value);
        const specialPrices = formatter.format(product.price.regularPrice.amount.value);
        
      
        if (status !== "IN_STOCK") {
            productDiv.classList.add("products_items_outof_stock");
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
        } else {
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
}

function incrementProductQun(event,index){
let inputBoxs=document.querySelectorAll("[type=number]")
    let inputValue;
    inputValue=parseInt(inputBoxs[index].value);
    inputValue++;
    if(inputValue>1){
        document.querySelectorAll(".btn_decrement")[index].removeAttribute("disabled");
        document.querySelectorAll(".btn_decrement")[index].style.cursor = "pointer";
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
        document.querySelectorAll(".btn_decrement")[index].style.cursor = "not-allowed";
       
    }

    inputBoxs[index].value=inputValue;
    


}


