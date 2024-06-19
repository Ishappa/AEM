
document.addEventListener("DOMContentLoaded", function () {
  var queryTypeCheckboxes = document.querySelectorAll(
    'input[type="checkbox"][name="queryType"]'
  );
  var colorCheckboxes = document.querySelectorAll(
    'input[type="checkbox"][name="color"]'
  );
  var resetButton = document.getElementById("resetFilterButton");
  var sortingDropdown = document.getElementById("sortingDropdown");
  var minPriceElement = document.getElementById("minPrice");
  var maxPriceElement = document.getElementById("maxPrice");
  // Initialize sorting dropdown and price range slider
  initSortingDropdown();
  initPriceRangeSlider();
  initColorsFromLocalStorage();
  // Initialize filter state
  var filterApplied = false;
  // Function to initialize sorting dropdown and store default sorting values
  function initSortingDropdown() {
    // Retrieve the sorting dropdown value from local storage
    var sortValue = localStorage.getItem("sortValue");
    if (sortValue) {
      sortingDropdown.value = sortValue;
    }
    // Add event listener to update local storage when dropdown value changes
    sortingDropdown.addEventListener("change", function (event) {
      localStorage.setItem("sortValue", sortingDropdown.value);
    });
  }
  // Function to initialize price range slider and set default price values
  function initPriceRangeSlider() {
    // Retrieve stored min and max prices from localStorage
    var storedMinPrice = localStorage.getItem("minPrice");
    var storedMaxPrice = localStorage.getItem("maxPrice");
    // Convert stored prices to integers or set defaults
    var minPrice = storedMinPrice ? parseInt(storedMinPrice) : 2000;
    var maxPrice = storedMaxPrice ? parseInt(storedMaxPrice) : 60000;
    $("#priceRange").slider({
      range: true,
      min: 2000,
      max: 60000,
      values: [minPrice, maxPrice],
      slide: function (event, ui) {
        // Update displayed price values
        minPriceElement.textContent = ui.values[0];
        maxPriceElement.textContent = ui.values[1];
      },
      stop: function (event, ui) {
        // Store updated price values in localStorage
        localStorage.setItem("minPrice", ui.values[0]);
        localStorage.setItem("maxPrice", ui.values[1]);
        // Fetch data with updated price values and other parameters

        fetchDataFromServlet(
          localStorage.getItem("checkboxValue"),
          localStorage.getItem("sortAttribute"),
          localStorage.getItem("sortDirection"),
          ui.values[0],
          ui.values[1],
          getSelectedColors()
        );
        // Filter is applied
        filterApplied = true;
        updateResetButtonVisibility();
      },
    });
  }
  // Function to initialize colors from local storage
  function initColorsFromLocalStorage() {
    var selectedColors =
      JSON.parse(localStorage.getItem("selectedColors")) || [];
    colorCheckboxes.forEach(function (checkbox) {
      if (selectedColors.includes(checkbox.value)) {
        checkbox.checked = true;
      }
    });
  }
  // Function to fetch data from servlet
  function fetchDataFromServlet(
    filterIn,
    sortAtr,
    sortDir,
    minPr,
    maxPr,
    colors
  ) {
    var servletUrl =
      "/bin/shureMicroFilter?in=" +
      filterIn +
      "&sortAttribute=" +
      sortAtr +
      "&sortDirection=" +
      sortDir +
      "&minPrice=" +
      minPr +
      "&maxPrice=" +
      maxPr +
      "&colour=" +
      colors;
    var xhr = new XMLHttpRequest();
    xhr.open("POST", servletUrl, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
      if (xhr.readyState === XMLHttpRequest.DONE) {
        if (xhr.status === 200) {
          var responseData = JSON.parse(xhr.responseText);
          displayProducts(responseData);
        } else {
          console.error("Request failed. Status: " + xhr.status);
        }
      }
    };
    xhr.send();
  }
  // Function to update the visibility of the reset button
  function updateResetButtonVisibility() {
    if (filterApplied) {
      // Update based on filter
      resetButton.style.display = "block";
    } else {
      resetButton.style.display = "none";
    }
  }
  // Event listener for queryType checkboxes

  queryTypeCheckboxes.forEach(function (checkbox) {
    checkbox.addEventListener("change", function (event) {
      queryTypeCheckboxes.forEach(function (checkbox) {
        checkbox.checked = false;
      });
      checkbox.checked = true;
      localStorage.setItem("checkboxValue", checkbox.value);
      fetchDataFromServlet(
        checkbox.value,
        localStorage.getItem("sortAttribute"),
        localStorage.getItem("sortDirection"),
        Number(minPriceElement.textContent),
        Number(maxPriceElement.textContent),
        getSelectedColors()
      );
      // Filter is applied
      filterApplied = true;
      updateResetButtonVisibility();
    });
  });
  // Event listener for color checkboxes
  colorCheckboxes.forEach(function (checkbox) {
    checkbox.addEventListener("change", function (event) {
      var selectedColors = [];
      colorCheckboxes.forEach(function (checkbox) {
        if (checkbox.checked) {
          selectedColors.push(checkbox.value);
        }
      });
      localStorage.setItem("selectedColors", JSON.stringify(selectedColors));
      fetchDataFromServlet(
        localStorage.getItem("checkboxValue"),
        localStorage.getItem("sortAttribute"),
        localStorage.getItem("sortDirection"),
        Number(minPriceElement.textContent),
        Number(maxPriceElement.textContent),
        selectedColors
      );
      // Filter is applied
      filterApplied = true;
      updateResetButtonVisibility();
    });
  });
  // Event listener for sorting dropdown
  sortingDropdown.addEventListener("change", function (event) {
    var sortValue = sortingDropdown.value;
    var sortParams = getSortParams(sortValue);
    localStorage.setItem("sortAttribute", sortParams.sortAttribute);
    localStorage.setItem("sortDirection", sortParams.sortDirection);
    fetchDataFromServlet(
      localStorage.getItem("checkboxValue"),
      sortParams.sortAttribute,
      sortParams.sortDirection,
      Number(minPriceElement.textContent),
      Number(maxPriceElement.textContent),
      getSelectedColors()
    );
   
  });
  // Event listener for reset button
  resetButton.addEventListener("click", function () {
    queryTypeCheckboxes.forEach(function (checkbox) {
      checkbox.checked = false;
    });
    colorCheckboxes.forEach(function (checkbox) {
      checkbox.checked = false;
    });
    localStorage.removeItem("selectedColors");
    localStorage.setItem("checkboxValue", "");
    localStorage.setItem("minPrice", "2000"); // Set default minimum price
    localStorage.setItem("maxPrice", "60000"); // Set default maximum price
    // Update slider values in the UI
    $("#priceRange").slider("values", [2000, 60000]);
    // Update HTML elements displaying the minimum and maximum prices
    minPriceElement.textContent = "2000";
    maxPriceElement.textContent = "60000";
    // Reinitialize the filter with locally stored values
    initializeFilter();
    // Reset flags
    filterApplied = false;
    updateResetButtonVisibility();
  });
  // Function to get selected colors
  function getSelectedColors() {
    var selectedColors = [];
    colorCheckboxes.forEach(function (checkbox) {
      if (checkbox.checked) {
        selectedColors.push(checkbox.value);
      }
    });
    return selectedColors;
  }
  // Function to get sort parameters based on sort value
  function getSortParams(sortValue) {
    var sortAttribute = "";
    var sortDirection = "";
    switch (sortValue) {
      case "defaults":
        sortAttribute = "position";
        sortDirection = "ASC";
        break;
      case "atoz":
        sortAttribute = "name";
        sortDirection = "ASC";
        break;
      case "ztoa":
        sortAttribute = "name";
        sortDirection = "DESC";
        break;
      case "lowtohigh":
        sortAttribute = "price";
        sortDirection = "ASC";
        break;
      case "hightolow":
        sortAttribute = "price";
        sortDirection = "DESC";
        break;
    }
    return {
      sortAttribute: sortAttribute,
      sortDirection: sortDirection,
    };
  }
  // Function to initialize the filter with locally stored values
  function initializeFilter() {
    var minPrice = localStorage.getItem("minPrice")? parseInt(localStorage.getItem("minPrice")): 2000;
    var maxPrice = localStorage.getItem("maxPrice")? parseInt(localStorage.getItem("maxPrice")): 60000;
    var selectedColors = getSelectedColors();
    var sortAttribute = localStorage.getItem("sortAttribute");
    var sortDirection = localStorage.getItem("sortDirection");
    var checkboxValue = localStorage.getItem("checkboxValue");
      
       minPriceElement.textContent = minPrice;
        maxPriceElement.textContent = maxPrice;
      
    // Set the initial checkbox states
    queryTypeCheckboxes.forEach(function (checkbox) {
      checkbox.checked = checkbox.value === checkboxValue;
    });
    // Set the initial sorting dropdown value
    if (sortAttribute && sortDirection) {
      var sortValue = getSortValueFromAttributes(sortAttribute, sortDirection);
      sortingDropdown.value = sortValue;
    }
    // Fetch data with stored values
    fetchDataFromServlet(
      checkboxValue,
      sortAttribute,
      sortDirection,
      minPrice,
      maxPrice,
      selectedColors
    );
    // Update filter and sorting flags
    filterApplied = true;
    updateResetButtonVisibility();
  }
  // Function to get sorting dropdown value from sort attributes
  function getSortValueFromAttributes(sortAttribute, sortDirection) {
    switch (sortAttribute + "-" + sortDirection) {
      case "position-ASC":
        return "defaults";
      case "name-ASC":
        return "atoz";
      case "name-DESC":
        return "ztoa";
      case "price-ASC":
        return "lowtohigh";
      case "price-DESC":
        return "hightolow";
      default:
        return "defaults";
    }
  }
  // Call initializeFilter function during initialization phase
  initializeFilter();
});
