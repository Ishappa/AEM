document.addEventListener("DOMContentLoaded", function() {
    // Get reference to the checkbox and text input
    var transformationCheckbox = document.getElementById('transformationCheckbox');
    var transformationProperty = document.getElementById('transformationProperty');

    // Hide the transformation property initially
    transformationProperty.style.display = 'none';

    // Add event listener to the checkbox
    transformationCheckbox.addEventListener('change', function() {
        // If checkbox is checked, show the transformation property input
        if (transformationCheckbox.checked) {
            transformationProperty.style.display = 'block';
        } else {
            // Otherwise, hide it
            transformationProperty.style.display = 'none';
        }
    });
});
