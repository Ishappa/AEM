
(function($, Coral) {
    "use strict";

    var registry = $(window).adaptTo("foundation-registry");

    registry.register("foundation.validation.validator", {
        selector: "[data-validation=multifield-validate]",
        validate: function(element) {
            var el = $(element);
            let max=el.data("max-items");
            let min=el.data("min-items");
            let items=el.children("coral-multifield-item").length;
            let domitems=el.children("coral-multifield-item");

            if(items>max){
                alert("you reached a maximum limt");
				domitems.last().remove();
              return "You can add maximum "+max+" books. You are trying to add "+items+"th book."

            }
            if(items<min){
                return "You add minimum "+min+" books."
            }
        }
    });

    registry.register("foundation.validation.validator", {
        selector: "[data-validation=firstname-validation]",
        validate: function(element) {
            let el = $(element);
            let pattern = /^[A-Z][a-z]*$/;
            let value=el.val();
            if(!pattern.test(value)){
               return "First name should start with Upper Case";
            }

        }
    });


})(jQuery, Coral);

