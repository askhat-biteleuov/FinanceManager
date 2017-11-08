$(document).ready(function () {
    var date = new Date();
    $('#yearSelect').val(date.getFullYear());
    /*$('#linechartForm').submit(function (event) {
     event.preventDefault();
     refreshLineChart();
     });*/
    $('#tab-by-year').click(function(){
        setTimeout(refreshLineChartYear, 100);
    });
    $('#accountSelectYear, #yearSelectYear').on('change', function () {
        refreshLineChartYear();
    });
    $('.checkboxTypesYear').on('click', function () {
        refreshLineChartYear();
    });

});



function LoadLineChartYear(year, accountName, types) {
    $.ajax({
        type: 'POST',
        beforeSend: function (request) {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            request.setRequestHeader(header, token);
        },
        contentType: 'application/json; charset=UTF-8',
        url: '/statistics/year',
        data: JSON.stringify({
            "year": year,
            "accountName": accountName,
            "types": types
        })
    }).done(function (data) {
        drawLineChartYear(data);
    }).fail(function (error) {
        alert('FAIL ' + error);
    });
}
