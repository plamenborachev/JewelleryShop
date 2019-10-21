let productDiv = $('.products-data');

// let fetchUrl = 'http://localhost:8000/top-offers/fetch/';
let fetchUrl = 'https://jewellery-shop.herokuapp.com/top-offers/fetch/';

$(document).ready(function () {
    $('#allRadio').attr('checked', true);

    fetch(fetchUrl + 'all', { mode: 'no-cors' })
        .then((response) => response.json())
        .then((json) => {
            productDiv.empty();

            if (json.length === 0) {
                productDiv.append(`<h3 class="text-center font-weight-bold text-danger">There are no jewelleries in the ${category} category.</h3>`)
            } else {
                for (let i = 0; i < json.length; i += 3) {
                    productDiv.append('<div class="products-row row d-flex justify-content-around mt-5">');
                    if (i < json.length){
                        $('.products-data .products-row:last-child').append(formatProduct(json[i]));
                    }
                    if (i + 1 < json.length){
                        $('.products-data .products-row:last-child').append(formatProduct(json[i + 1]));
                    }
                    if (i + 2 < json.length){
                        $('.products-data .products-row:last-child').append(formatProduct(json[i + 2]));
                    }
                }
            }
        })
});

$('input[type=radio][name=selection]').change(function () {
    let category = $(this).val();

    fetch(fetchUrl + category, { mode: 'no-cors' })
        .then((response) => response.json())
        .then((json) => {
            productDiv.empty();
            if (json.length === 0) {
                productDiv.append(`<h3 class="text-center font-weight-bold text-danger">There are no jewelleries on sale in the "${category}" category.</h3>`)
            } else {
                for (let i = 0; i < json.length; i += 3) {

                    productDiv.append('<div class="products-row row d-flex justify-content-around mt-5">');
                    if (i < json.length) {
                        $('.products-data .products-row:last-child').append(formatProduct(json[i]));

                    }
                    if (i + 1 < json.length){
                        $('.products-data .products-row:last-child').append(formatProduct(json[i + 1]));
                    }
                    if (i + 2 < json.length){
                        $('.products-data .products-row:last-child').append(formatProduct(json[i + 2]));
                    }
                }
            }
        })
});

function formatProduct(product) {
    if (product.product.quantity === 0){
        return '<div class="product data col-md-3 p-1 text-center">'
            + `<img src="${product.product.imageUrl}" class="product-image-home img-thumbnail px-auto" alt="Image not loaded..."/>`
            + `<h5 class="text-center font-weight-bold mt-3">${product.product.name}</h5>`
            + `<h6 class="text-center font-weight-bold text-secondary">${product.price.toFixed(2)}&euro;</h6>`
            + `<h6 class="text-center text-muted"><del>${product.product.price.toFixed(2)}&euro;</del></h6>`
            + '<h5 class="text-danger font-weight-bolder">Out of stock</h5>'
            + '</div>'
    }
    return '<div class="product data col-md-3">'
        + `<a href="/products/details/${product.product.id}"><img src="${product.product.imageUrl}" class="product-image-home img-thumbnail px-auto" alt="Image not loaded..."/></a>`
        + `<h5 class="text-center font-weight-bold mt-3">${product.product.name}</h5>`
        + `<h6 class="text-center font-weight-bold text-secondary">${product.price.toFixed(2)}&euro;</h6>`
        + `<h6 class="text-center text-muted"><del>${product.product.price.toFixed(2)}&euro;</del></h6>`
        + '</div>'
}