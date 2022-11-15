
	// Cart add remove functions
	var cart = {
		'add': function(product_id, quantity) {
			addProductNotice('Yee Shop Notification', '<img th:src="[[@{/image/demo/shop/product/e11.jpg}]]" alt="">', '<h3><a href="#" th:text="Apple Yee">This product </a> is added to <a href="#">shopping cart</a>!</h3>', 'success');
		}
	}

	var wishlist = {
		'add': function(product_id) {
			addProductNotice('Yee Shop Notification', '<img th:src="[[@{/image/demo/shop/product/e11.jpg}]]" alt="">', '<h3><a href="#" th:text="Apple Yee">This product </a> is added to <a href="#">Wish List</a>!</h3>', 'success');
		}
	}
	var compare = {
		'add': function(product_id) {
			addProductNotice('Product added to compare', '<img src="image/demo/shop/product/e11.jpg" alt="">', '<h3>Success: You have added <a href="#">Apple Cinema 30"</a> to your <a href="#">product comparison</a>!</h3>', 'success');
		}
	}

	/* ---------------------------------------------------
		jGrowl – jQuery alerts and message box
	-------------------------------------------------- */
	function addProductNotice(title, thumb, text, type) {
		$.jGrowl.defaults.closer = false;
		//Stop jGrowl
		//$.jGrowl.defaults.sticky = true;
		var tpl = thumb + '<h3>'+text+'</h3>';
		$.jGrowl(tpl, {		
			life: 4000,
			header: title,
			speed: 'slow',
			theme: type
		});
	}

	$(document).ready(function() {
		// $("div.ct1[data-id] input").on("input", function(){
		// 	var id = $(this).closest("div").attr("data-id");
		// 	var price = $(this).closest("div").attr("data-price");
		// 	var discount = $(this).closest("div").attr("data-discount");
		// 	var qty = $(this).val();
		// 	$.ajax({
		// 		url:`/cart/update/${id}/${qty}`,
		// 		success: function(response){
		// 			$(".cart-cnt").html(response[0]);
		// 			$(".cart-amt").html(response[1]);
					
		// 		}
		// 	});
		// 	var amt = qty*price*(1-discount);
		// 	$(this).closest("div").find("div#amt").html(amt +" VNĐ");
			
		// });
		
		//remove cart
		// $(".btn-cart-remove").click(function() {
		// 	var id = $(this).closest("tr").attr("data-id");
		// 	$.ajax({
		// 		url:"/cart/remove/"+id,
		// 		success: function(response){
		// 			$(".cart-cnt").html(response[0]);
		// 			$(".cart-amt").html(response[1]);
					
		// 		}
		// 	});
		// 	$(this).closest("tr").remove();
		// 	location.reload();
		// });

		//cart item remove
		$(".btn-cart-item-remove").on("click", function() {
			var id = $(this).closest("button").attr("data-id");
			$.ajax({
				url:"/cart/remove/"+id,
				success: function(response){
					//$(".cart-cnt").html(response[0]);
					//$(".cart-amt").html(response[1]);
					//setTimeout(() => {  location.reload(); }, 1000);
					location.reload();
				}
			});
			
			// var img = $(this).closest(".thumbnail").find("a>img");
			// var options = {to:'#cart-img', className:'cart-fly'}
			// var cart_css='.cart-fly{background-image: url("'+img.attr("src")+'");background-size: 100% 100%;}';
			// $("style#cart-css").html(cart_css);
			// img.effect('transfer', options, 500);
		
		});

		//cart clear
		$(".btn-cart-clear").on("click",function() {
			$.ajax({
				url:"/cart/clear",
				success: function(response){
					// $(".cart-cnt").html(0);
					// $(".cart-amt").html(0);
					// $("table>tbody").html("");
					setTimeout(() => {  location.reload(); }, 1000);

				}
			});
			// location.reload();
		});
		
		//add Shopping cart
		$(".btn-add-to-cart").on("click", function() {
			var id = $(this).closest("button").attr("data-id");
	
			$.ajax({
				url:"/cart/add/"+id,
				success: function(response){
					//$(".cart-cnt").html(response[0]);
					//$(".cart-amt").html(response[1]);
					setTimeout(() => {  location.reload(); }, 500);
					
				}
			});
			
			// var img = $(this).closest(".thumbnail").find("a>img");
			// var options = {to:'#cart-img', className:'cart-fly'}
			// var cart_css='.cart-fly{background-image: url("'+img.attr("src")+'");background-size: 100% 100%;}';
			// $("style#cart-css").html(cart_css);
			// img.effect('transfer', options, 500);
		
		});
		$(".btn-add-to-cart2").on("click", function() {
			var id = $(this).closest("input").attr("data-id");
			var qty= document.getElementById("qty-input").value;
			$.ajax({
				url:"/cart/add2/"+id+"/"+qty,
				success: function(response){
					//$(".cart-cnt").html(response[0]);
					//$(".cart-amt").html(response[1]);
					setTimeout(() => {  location.reload(); }, 500);
					
				}
			});
			
			// var img = $(this).closest(".thumbnail").find("a>img");
			// var options = {to:'#cart-img', className:'cart-fly'}
			// var cart_css='.cart-fly{background-image: url("'+img.attr("src")+'");background-size: 100% 100%;}';
			// $("style#cart-css").html(cart_css);
			// img.effect('transfer', options, 500);
		
		});
		//cart update
		$(".btn-update-cart-item").on("click",function(){
			var id= $(this).closest("button").attr("data-id");
			var qty= document.getElementById("qty-update").value;
			$.ajax({
				url: "/cart/update/"+id+"/"+qty,
				success:function(response){
					//setTimeout(() =>{location.reload();},1000)
					location.reload();
				}
			});
		});
		//email dialog
		// $(".btn-open-dialog").click(function() {
		// 	var id = $(this).closest("div").attr("data-id");
		// 	$("#myModal #id").val(id);
		// });
		
		//email send
		// $(".btn-send").click(function() {
		// 	var form={
		// 	 id: $("#myModal #id").val(),
		// 	 to: $("#myModal #sender").val(),
		// 	 body: $("#myModal #comments").val(),
		// 	 from: $("#myModal #email").val()
		// 	}
		// 	$.ajax({
		// 		url:"/product/send-to-friend",
		// 		data:form,
		// 		type:'post',
		// 		success: function(response){
		// 			if(response){
		// 				$("[data-dismiss]").click();
		// 				alert("Da gui thanh cong")
		// 			}
		// 			else{
		// 				alert("Loi gui mail")
		// 		}
		// 		}
		// 	})
		// });
		
		//button like
		$(".btn-add-to-wishlist").click(function() {
			var id = $(this).closest("a").attr("data-id");
			$.ajax({
				url:"/product/add-to-favo/"+id,
	
				success: function(response){
					//  alert("Bạn đã thích sản phẩm thành công!");
				}
			})
		});
	});