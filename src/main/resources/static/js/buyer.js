"use strict";

(function($) {
    const app = {
        init: function() {
            this.bindEvents();


        },
        bindEvents: function() {
            $(".btn-follow").on("click", (e) => {
                let id = e.target.id;
                this.follow(id);
            });

            $(".btn-unfollow").on("click", (e) => {
                let id = e.target.id;
                this.unfollow(id);
            });
        },
        unfollow: function(id) {
            console.log("id: "+ id);
            $.ajax ({
                url: '/buyer/unfollow/'+id,
                type: "PUT",
                dataType: "json",
                contentType: "application/json",
                success: (data) => {
                    console.log("ajax: " + data);
                    location.reload();
                },
                error: (error) => {
                    console.log("Error: " + error);
                    location.reload();
                }
            });
        },

        follow: function(id) {
            console.log("id: "+ id);
            $.ajax ({
                url: '/buyer/follow/'+id,
                type: "PUT",
                dataType: "json",
                contentType: "application/json",
                success: (data) => {
                    console.log("ajax: " + data);
                    location.reload();
                },
                error: (error) => {
                    console.log("Error: " + error);
                    location.reload();
                }
            });
        }
    }
    app.init();

})(jQuery);
