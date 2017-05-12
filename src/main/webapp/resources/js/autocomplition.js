$(document).ready(function () {
    $('.hashtagSearch').on('keyup change', function () {
        var hashtag = $(this).val();
        $.ajax({
            type: 'GET',
            contentType: 'application/json; charset=UTF-8',
            url: "/hashtag",
            data: ({"str": hashtag})
        }).done(function (data) {
            var dropdown='<ul class="dropdown-menu">';
            var i;
            for (i = 0; i < data.length; ++i) {
                dropdown+='<li><a href="#">'+data[i]+'</a></li>';
            }
            dropdown+='</ul>';
            $('.hashtagSearch').after(dropdown);
            $(this).dropdown('show');
        }).fail(function (error) {
        });
    });
});
