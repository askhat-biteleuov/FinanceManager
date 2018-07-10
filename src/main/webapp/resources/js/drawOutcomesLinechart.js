$(document).ready(function () {
    google.charts.load('current', {packages: ['corechart', 'line']});
    google.charts.setOnLoadCallback(refreshLineChart);
});
function drawLineChart(data) {

    var dataTable = new google.visualization.DataTable();

    dataTable.addColumn('number', 'День');

    for (var type_ in data[1]) {
        dataTable.addColumn('number', type_)
    }

    var numOfTypes = Object.keys(data[1]).length;
    var days = [];

    for (var day in data) {
        var dataArr = [numOfTypes + 1];
        days.push(parseInt(day));
        dataArr[0] = parseInt(day);
        var i = 1;
        for (var type in data[day]) {
            dataArr[i] = (data[day])[type];
            i++;
        }
        dataTable.addRow(dataArr);
    }


    var options = {

        title: "Сумма расходов по категориям за день",
        backgroundColor: 'transparent',
        hAxis: {
           /* viewWindowMode: 'explicit',
            viewWindow: {
                max: Object.keys(data).length,
                min: 1
            }*/
            ticks: days
        }
    };

    var chart = new google.visualization.LineChart(document.getElementById('linechart'));
    chart.draw(dataTable, options);
}

function refreshLineChart() {

    var types = [];
    $('.checkboxTypes input:checked').each(function () {
        types.push($(this).val());
    });
    LoadLineChart($("#linechartForm [name=month]").val(), $("#linechartForm [name=year]").val(),
        $('#linechartForm [name=accountName]').val(), types);
}
