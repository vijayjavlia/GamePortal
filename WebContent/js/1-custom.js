$('.bigslide-inner .owl-carousel').owlCarousel({
    loop:false,
    margin:5,
    responsiveClass:true,
    responsive:{
        0:{
            items:1,
        },
        600:{
            items:1,
        },
        1000:{
            items:1,
        }
    }
})
$('.small-slideinner .owl-carousel').owlCarousel({
    loop:false,
    margin:10,
    dots: false,
    nav: false,
    responsiveClass:true,
    responsive:{
        0:{
            items:3,
        },
        600:{
            items:4,
        },
        1000:{
            items:6,
        }
    }
})
