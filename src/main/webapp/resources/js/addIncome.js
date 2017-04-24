$(document).ready(function () {

    $('#adding').on('click', 'button', function () {
        var form = $(this).next('form');
        form.slideToggle();
        form.submit(function (event) {
            event.preventDefault(); // prevent form from POST to server

            if (!$('#amount').val()) {
                if ($("#amount").parent().next(".validation").length == 0) {
                    $("#amount").parent().after(
                        "<div class='validation' style='color:red;margin-bottom: 20px;'>Please enter amount</div>"
                    );
                }
            } else {
                var formData = {
                    'amount': $('input[name=amount]').val(),
                    'date': $('input[name=date]').val(),
                    'note': $('input[name=note]').val(),
                    'accountId': $('input[name=accountId]').val(),
                };
                $("#amount").parent().next(".validation").remove(); // remove it
                sendAjax(formData, form);
            }
        });
    });
    var now = new Date();
    var day = ("0" + now.getDate()).slice(-2);
    var month = ("0" + (now.getMonth() + 1)).slice(-2);
    var today = now.getFullYear()+"-"+(month)+"-"+(day) ;
    $('#incomeForm [name=date]').val(today);

    function sendAjax(data, form) {
        $.ajax({
            type: 'POST',
            beforeSend: function (request) {
                var token = $("meta[name='_csrf']").attr("content");
                var header = $("meta[name='_csrf_header']").attr("content");
                request.setRequestHeader(header, token);
            },
            contentType: 'application/json; charset=UTF-8',
            url: form.attr('action'),
            data: JSON.stringify(data)
        }).done(function (data) {
            alert('SUCCESS');
        }).fail(function (error) {
            alert('FAIL ' + error);
        });
    }
});