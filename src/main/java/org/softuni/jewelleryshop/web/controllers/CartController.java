package org.softuni.jewelleryshop.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.jewelleryshop.GlobalConstants;
import org.softuni.jewelleryshop.domain.models.service.OrderServiceModel;
import org.softuni.jewelleryshop.domain.models.view.OrderProductViewModel;
import org.softuni.jewelleryshop.domain.models.view.ProductDetailsViewModel;
import org.softuni.jewelleryshop.domain.models.view.ShoppingCartItem;
import org.softuni.jewelleryshop.service.OrderService;
import org.softuni.jewelleryshop.service.ProductService;
import org.softuni.jewelleryshop.service.UserService;
import org.softuni.jewelleryshop.web.annotations.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController extends BaseController {

    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @Autowired
    public CartController(ProductService productService, UserService userService, OrderService orderService, ModelMapper modelMapper) {
        this.productService = productService;
        this.userService = userService;
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }


    @PostMapping("/add-product")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addToCartConfirm(String id, Integer quantity, HttpSession session) {

        if (quantity == null){
            throw new IllegalArgumentException(GlobalConstants.QUANTITY_EXCEPTION_MESSAGE);
        }

        if (quantity <= 0){
            throw new IllegalArgumentException(GlobalConstants.POSITIVE_QUANTITY_EXCEPTION_MESSAGE);
        }

        ProductDetailsViewModel product = this.modelMapper
                .map(this.productService.findProductById(id), ProductDetailsViewModel.class);

        OrderProductViewModel orderProductViewModel = new OrderProductViewModel();
        orderProductViewModel.setProduct(product);

        if (product.getDiscountedPrice() != null){
            orderProductViewModel.setPrice(product.getDiscountedPrice());
        } else {
            orderProductViewModel.setPrice(product.getPrice());
        }
        ShoppingCartItem cartItem = new ShoppingCartItem();
        cartItem.setProduct(orderProductViewModel);
        cartItem.setQuantity(quantity);

        var cart = this.retrieveCart(session);
        this.addItemToCart(cartItem, cart);

        return redirect("/cart/details");
    }

    @GetMapping("/details")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Cart Details")
    public ModelAndView cartDetails(ModelAndView modelAndView, HttpSession session) {
        var cart = this.retrieveCart(session);
        modelAndView.addObject("totalPrice", this.orderService.calcTotal(cart));
        return view("cart/cart-details", modelAndView);
    }

    @DeleteMapping("/remove-product")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView removeFromCartConfirm(String id, HttpSession session) {
        this.removeItemFromCart(id, this.retrieveCart(session));
        return redirect("/cart/details");
    }

    @PostMapping("/checkout")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView checkoutConfirm(HttpSession session, Principal principal) {
        var cart = this.retrieveCart(session);
        OrderServiceModel orderServiceModel = this.orderService.prepareOrder(cart, principal.getName());
        if (orderServiceModel.getProducts().isEmpty()){
            throw new IllegalArgumentException(GlobalConstants.CART_EMPTY_EXCEPTION_MESSAGE);
        }
        this.orderService.createOrder(orderServiceModel);
        cart.clear();
        return redirect("/orders/my");
    }

    private List<ShoppingCartItem> retrieveCart(HttpSession session) {
        this.initCart(session);
        return (List<ShoppingCartItem>) session.getAttribute("shopping-cart");
    }

    private void initCart(HttpSession session) {
        if (session.getAttribute("shopping-cart") == null) {
            session.setAttribute("shopping-cart", new LinkedList<>());
        }
    }

    private void addItemToCart(ShoppingCartItem item, List<ShoppingCartItem> cart) {
        for (ShoppingCartItem shoppingCartItem : cart) {
            if (shoppingCartItem.getProduct().getProduct().getId().equals(item.getProduct().getProduct().getId())) {
                shoppingCartItem.setQuantity(shoppingCartItem.getQuantity() + item.getQuantity());
                return;
            }
        }
        cart.add(item);
    }

    private void removeItemFromCart(String id, List<ShoppingCartItem> cart) {
        cart.removeIf(ci -> ci.getProduct().getProduct().getId().equals(id));
    }


}
