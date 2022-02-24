/* global jQuery, Coral */
(function($, Coral) {
    "use strict";

    console.log(" --------CLIENTLIBS LOADED------- ");

    var registry = $(window).adaptTo("foundation-registry");

    // Validator for required for multifield max and min items
    registry.register("foundation.validation.validator", {
        selector: "[data-validation=mysite-multifield]",
        validate: function(element) {
            var el = $(element);
            let max=el.data("max-items");
            let min=el.data("min-items");
            let items=el.children("coral-multifield-item").length;


            console.log("{} : {} :{} ",max,min,items);
            if(items>max){
              /* Use below line if you don't want to add item in multifield more than max limit */
              //domitems.last().remove();
              return "You can add maximum "+max+" books. You are trying to add "+items+"th book."
            }
            if(items<min){
                return "You add minimum "+min+" books."
            }
        }
    });


    // Validator for required for multifield max and min items
    registry.register("foundation.validation.validator", {
        selector: "[data-validation=mysite-firstname-validation]",
        validate: function(element) {
            var el = $(element);
            let pattern=/[0-9a-z]/;
            let value=el.val();
            if(pattern.test(value)){
                return "Please add Upper Case Letters Only";
            }
        }
    });
    })(jQuery, Coral);