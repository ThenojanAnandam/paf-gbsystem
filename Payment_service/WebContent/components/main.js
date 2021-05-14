$(document).ready(function()
{
	 $("#alertSuccess").hide();
	 $("#alertError").hide();
}); 


$(function (){
	var $apps = $('#apps');
	var $cardName = $('#cardName');
	var $cardNo = $('#cardNo');
	var $zipCode = $('#zipCode');
	var $bid = $('#BID');
	var $cid = $('#CID');
	

	$.ajax({
		type: 'GET',
		url: 'http://localhost:8080/Payment/webapi/Payment/payment',
		success: function(apps){
			//console.log('success',data);
			$.each(apps, function(i, app){
				$apps.append('<li><div class="card shadow-lg p-3 mb-5 bg-white rounded bg-light m-2\" style=\"width: 12rem;float: left;">'
							+'Card Name:<span class="noedit cardName">' + app.cardName +'</span><input class="edit cardName"/>'+'<br>'
							+'Card No:<span class="noedit cardNo">'+ app.cardNo +'</span><input class="edit cardNo"/> '+'<br>'
							+'ZipCode:<span class="noedit cipCode">'+ app.zipCode +'</span><input class="edit zipCode"/> '+'<br>'
							+'BID:<span class="noedit BID">'+ app.BID +'</span><input class="edit bid"/> '+'<br>'
							+'CID:<span class="noedit CID">'+ app.CID +'</span><input class="edit cid"/>'+' <br>'
							+'<input type="button" id="'+ app.PID +'" value="Remove" class="btn btn-outline-danger remove">'+'<br>'
							+'<input type="button" " value="Edit" class="editapp btn btn-outline-primary noedit">'+'<br>'
							+'<input type="button" " value="Save" class="saveedit btn btn-outline-success edit">'+'<br>'
							+'<input type="button" " value="Cancel" class="canceledit btn btn-outline-danger edit"></li>');

			});
		},
		error: function() {
			alert('Payment loading error...');
		}
	});
	
	
	$('#btnSave').on('click', function(){
		
		//clear status messages
		$("#alertSuccess").text("");
		$("#alertSuccess").hide();
		$("#alertError").text("");
		$("#alertError").hide();
		
		//Form validation
		var status = validatePaymentForm(); 
		

		
		//Check not valid
		if (status != true)
		 {
			 $("#alertError").text(status);
			 $("#alertError").show();
			 return;
		 } 
		
		
        //IF valid		
		var app = {
				cardName: $cardName.val(),
				cardNo: $cardNo.val(),
				zipCode: $zipCode.val(),
				bid: $bid.val(),
				cid: $cid.val(),

		};
		

		
		$.ajax({
			headers: { 
		        'Accept': 'application/json',
		        'Content-Type': 'application/json' 
		    },
			type: 'POST',
			url: 'http://localhost:8080/Payment/webapi/Payment/payment/',
			data: JSON.stringify(app),
			dataType: 'json',
			success: function(newPayment){
				console.log("Inserted");
				$apps.append('<li><div class="card shadow-lg p-3 mb-5 bg-white rounded bg-light m-2\" style=\"width: 12rem;float: left;">'
						+'cardName:<span class="noedit cardName">' + newPayment.cardName +'</span><input class="edit cardName"/>'+'<br>'
						+'cardNo:<span class="noedit nic">'+ newPayment.cardNo +'</span><input class="edit cardNo"/> '+'<br>'
						+'zipCode:<span class="noedit zipCode">'+ newPayment.zipCode +'</span><input class="edit zipCode"/> '+'<br>'
						+'bid:<span class="noedit bid">'+ newPayment.bid +'</span><input class="edit bid"/>'+'<br>'
						+'cid:<span class="noedit cid">'+ newPayment.cid +'</span><input class="edit cid"/> '+'<br>'
						+'<input type="button" id="'+ newPayment.PID +'" value="Remove" class="btn btn-outline-danger remove">'+'<br>'
						+'<input type="button" " value="Edit" class="editapp btn btn-outline-primary noedit">'+'<br>'
						+'<input type="button" " value="Save" class="saveedit btn btn-outline-success edit">'+'<br>'
						+'<input type="button" " value="Cancel" class="canceledit btn btn-outline-danger edit"></li>');
				
				//Show Success Message
				$("#alertSuccess").text("Your Payement Details Saved Successfully");
				$("#alertSuccess").show();

				$("#formPayment")[0].reset(); 
				
			},
			
			error: function() {
				alert('Payment Saving Error');
			}
		});
		
		function validatePaymentForm()
		{
			// Card NAME
			if ($("#cardName").val().trim() == "")
			 {
			 return "Insert card name.";
			 }

			//Card No
			if ($("#cardNo").val().trim() == "")
			 {
			 return "Insert card no.";
			 }

			//ZipCode
			if ($("#zipCode").val().trim() == "")
			 {
			 return "Insert zipCode.";
			 }

			//bid
			if ($("#bid").val().trim() == "")
			 {
			 return "Insert bid.";
			 }

			if ($("#cid").val().trim() == "")
			 {
			 return "Insert cid.";
			 }

			return true;
		}
		

		
	});
	
	
	$apps.delegate('.remove','click',function(){
		var $li=$(this).closest('li');
		var self = this;
		$.ajax({
			type:'DELETE',
			url:'http://localhost:8080/Payment/webapi/Payment/payment/'+$(this).attr('id'),
			success: function(){
				console.log("Deleted");
				$(self);
				$li.fadeOut(300,function(){
					$(this).remove();
					
					
					
				})
				
			},
		
			error: function() {
				alert('Payment Delete Error');
			}
		});
	});
	
	
$apps.delegate('.editapp','click',function(){
		
		var $li=$(this).closest('li');
		
		$li.find('input.pid').val($li.find('span.pid').html());
		$li.find('input.cardName').val($li.find('span.cardName').html());
		$li.find('input.cardNo').val($li.find('span.cardNo').html());
		$li.find('input.zipCode').val($li.find('span.zipCode').html());
		$li.find('input.bid').val($li.find('span.bid').html());
		$li.find('input.cid').val($li.find('span.cid').html());
		$li.addClass('edit');
	});
	
	$apps.delegate('.canceledit','click',function(){
		$(this).closest('li').removeClass('edit');
		
	});
	
	$apps.delegate('.saveedit','click',function(){
		var $li=$(this).closest('li');
		var app={
				
				cardName: $li.find('input.cardName').val(),
				cardNo: $li.find('input.cardNo').val(),
				zipCode: $li.find('input.zipCode').val(),
				bid: $li.find('input.bid').val(),
				cid: $li.find('input.cid').val()
				
		};
		
		$.ajax({
			headers:{
				'Accept':'application/json',
				'Content-Type':'application/json'
					
					
			},
			type: 'PUT',
			url: 'http://localhost:8080/Payment/webapi/Payment/payment',
			data: JSON.stringify(app),
			dataType: 'json',
			
			success: function(){

				$li.find('span.cardName').html(app.cardName);
				$li.find('span.cardNo').html(app.cardNo);
				$li.find('span.zipCode').html(app.zipCode);
				$li.find('span.bid').html(app.bid);
				$li.find('span.cid').html(app.cid);
				$li.removeClass('edit');
				
				$("#alertSuccess").text("Your Payment Details Updated Successfully");
				$("#alertSuccess").show();
				},
		
				error: function(){
				alert('Payment Update Error');
			}
			
		});
	});
	
	
	
	
	
	
});