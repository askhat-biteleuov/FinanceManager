$(document).ready(function () {

    $('.hashtagSearchInput').on('keyup change click', function () {
        var hashtag = $(this).val();
        $('.hashtagSearchInput').dropdown('toggle');
        $.ajax({
            type: 'GET',
            contentType: 'application/json; charset=UTF-8',
            url: "/hashtag",
            data: ({"str": hashtag})
        }).done(function (data) {
            $('.hashtagSearchMenu').remove();
            var dropdown='<ul class="dropdown-menu hashtagSearchMenu">';
            var i;
            for (i = 0; i < data.length; ++i) {
                dropdown+='<li class="hashtagSearchLink"><a>'+data[i]+'</a></li>';
            }
            dropdown+='</ul>';
            $('.hashtagSearchInput').after(dropdown);
            $('.hashtagSearchInput').dropdown('toggle');
        }).fail(function (error) {
            $('.hashtagSearchMenu').remove();
        });
    });


        $('body').on("click",".hashtagSearchLink",function () {
            $('.hashtagSearchInput').val($(this).text());
            $('.hashtagSearchMenu').remove();
        });
});
