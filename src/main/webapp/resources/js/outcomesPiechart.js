$(document).ready(function () {
    $('#rangeDto').submit(function (event) {
        event.preventDefault();
        $.ajax({
            type: 'POST',
            beforeSend: function (request) {
                var token = $("meta[name='_csrf']").attr("content");
                var header = $("meta[name='_csrf_header']").attr("content");
                request.setRequestHeader(header, token);
            },
            contentType: 'application/json; charset=UTF-8',
            url: '/account/pagejson',
            data: JSON.stringify({
                "start": $("#rangeDto [name=start]").val(),
                "end": $("#rangeDto [name=end]").val(),
                "accountName": $('#rangeDto [name=accountName]').val()
            })
        }).done(function (data) {
            alert(data);
            drawChart(data);
        }).fail(function (error) {
            alert('FAIL ' + error);
        });
    });
});