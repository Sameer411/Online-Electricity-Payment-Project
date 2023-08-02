<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Razorpay Payment</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
	crossorigin="anonymous"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://checkout.razorpay.com/v1/checkout.js"></script>
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<div class="container-fluid">
			<a style="color: lightgrey" class="navbar-brand" href="#">Payment
				Page</a>
			<h3 style="color: grey; margin-right: 85vh">Electricity Billing
				System</h3>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav"
				aria-controls="navbarNav" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
		</div>
	</nav>
	<Form id="paymentForm" method="POST">
		<section class="vh-100" style="background-color: #3c4963;">
			<div class="container py-5 h-100">
				<div
					class="row d-flex justify-content-center align-items-center h-100">
					<div class="col-12 col-md-8 col-lg-6 col-xl-5">
						<div class="card shadow-2-strong" style="border-radius: 1rem;">
							<div class="card-body p-5 text-center">

								<div class="form-outline mb-4">
									<input type="text" id="billId" name="billId"
										class="form-control form-control-lg" /> <label
										class="form-label" for="typeEmailX-2">Enter BILL ID</label>
								</div>

								<button class="btn btn-primary btn-lg btn-block" id="payButton"
									type="button" onclick="javascript:initiatePayment()">Pay
									Now</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	</Form>
	<Form id="paymentResponseForm" action="/payment/response" method="POST">
        	<input type="hidden" name="paymentId" id="paymentId" />
        	<input type="hidden" name="orderId" id="orderId" />
        	<input type="hidden" name="signature" id="signature" />
        	<input type="hidden" name="billid" id="billid" />
        <!-- Add any additional input fields for other response data -->
    </Form>



	<script type="text/javascript">
        function initiatePayment() {
        	
            var billId = document.getElementById("billId").value;
            console.log("billid=",billId);
            // Make an API call to your backend to fetch the bill and create an order
            // Replace 'YOUR_BACKEND_ENDPOINT' with the actual backend endpoint for fetching bill and creating an order
            fetch('/payment/createOrder/'+billId, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ billId: billId })
            })
            .then(response => response.json())
            .then(data => {
                // Capture the order ID from the response
                const orderId = data.orderId;

                // Initialize the Razorpay checkout form
                var options = {
                    key: 'rzp_test_Y9WtUj185aAZ1G',  // Replace with your Razorpay API key
                    amount: data.amount,  // Payment amount in paise or smallest currency unit
                    currency: data.currency,
                    order_id: orderId,
                    name: 'Electricity Bill Payment',
                    description: 'Payment for bill'+orderId,
                    handler: function(response) {
                        // Handle the payment success response
                        console.log(response.razorpay_payment_id);
                        console.log(response.razorpay_order_id);
                        console.log(response.razorpay_signature);
                        handlePaymentSuccess(response,billId);
                    },
                    prefill: {
                        name: '',
                        email: ''
                    },
                    
                    theme:{
                    	color:"#3399cc"
                    }
                    
                    
                };
                var rzp = new Razorpay(options);
                rzp.open();
            })
            .catch(error => {
                console.error('Error:', error);
                // Handle the error response
            });
        }
        
        function handlePaymentSuccess(response,billId) {
            // Extract the required payment response data
            var paymentId = response.razorpay_payment_id;
            var orderId = response.razorpay_order_id;
            var signature = response.razorpay_signature;
            // Extract any other response data as needed
			console.log(paymentId)
			console.log(orderId)
			console.log(signature)
			console.log(billId)
            // Set the response data in the form inputs
            document.getElementById("paymentId").value = paymentId;
            document.getElementById("orderId").value = orderId;
            document.getElementById("signature").value = signature;
            document.getElementById("billid").value = billId;
            // Set other form inputs with the response data if required

            // Submit the form to the backend
            document.getElementById("paymentResponseForm").submit();
        }
        
        
    </script>
</body>
</html>
