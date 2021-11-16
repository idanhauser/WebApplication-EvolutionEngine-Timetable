
$(function() {

    $("#loginForm").submit(function() {
        $.ajax({
            data: $(this).serialize(),
            url: this.action,
            timeout: 2000,

            error: function (xhr, textStatus, err) {
                $("#login-error").text(xhr.responseText)
                console.log(xhr.responseText);
            },
            success: function(nextPageUrl) {
                window.location = nextPageUrl;
            }
        });

        // by default - we'll always return false so it doesn't redirect the user.
        return false;
    });
});