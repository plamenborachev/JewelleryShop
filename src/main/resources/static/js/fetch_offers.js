function formatProduct(product) {
    return '<div class="product">'
        + '<div class="text-center">'
        + `<a href="/products/details/${product.product.id}"><img src="${product.product.imageUrl}" class="product-image-home img-thumbnail px-auto" alt="Image not loaded..."/></a>`
        + '</div>'
        + `<h4 class="text-center font-weight-bold mt-3">${product.product.name}</h4>`
        + `<h5 class="text-center font-weight-bold">Promo Price: ${product.price.toFixed(2)}&euro;</h5>`
        + `<h5 class="text-center font-weight-bold">Original Price: ${product.product.price.toFixed(2)}&euro;</h5>`
        + '</div>'
}

$(document).ready(function () {
    $('#allRadio').attr('checked', true);

    fetch('http://localhost:8000/top-offers/fetch/all')
        .then((response) => response.json())
        .then((json) => {
            $('.products-data').empty();

            if (json.length === 0) {
                $('.products-data').append(`<h3 class="text-center font-weight-bold text-danger">There are no products in the ${category} category.</h3>`)
            } else {
                for (let i = 0; i < json.length; i += 3) {
                    $('.products-data').append('<div class="products-row row d-flex justify-content-around mt-5">');
                    if (i < json.length) $('.products-data .products-row:last-child').append(formatProduct(json[i]));
                    if (i + 1 < json.length) $('.products-data .products-row:last-child').append(formatProduct(json[i + 1]));
                    if (i + 2 < json.length) $('.products-data .products-row:last-child').append(formatProduct(json[i + 2]));
                }
            }
        })
});

$('input[type=radio][name=selection]').change(function () {
    let category = $(this).val();

    fetch('http://localhost:8000/top-offers/fetch/' + category)
        .then((response) => response.json())
        .then((json) => {
            $('.products-data').empty();
            console.log(json);
            if (json.length === 0) {
                $('.products-data').append(`<h3 class="text-center font-weight-bold text-danger">There are no products on sale in the "${category}" category.</h3>`)
            } else {
                for (let i = 0; i < json.length; i += 3) {
                    $('.products-data').append('<div class="products-row row d-flex justify-content-around mt-5">');
                    if (i < json.length) $('.products-data .products-row:last-child').append(formatProduct(json[i]));
                    if (i + 1 < json.length) $('.products-data .products-row:last-child').append(formatProduct(json[i + 1]));
                    if (i + 2 < json.length) $('.products-data .products-row:last-child').append(formatProduct(json[i + 2]));
                }
            }
        })
});