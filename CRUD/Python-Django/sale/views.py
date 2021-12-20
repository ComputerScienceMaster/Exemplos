# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.contrib.auth.decorators import login_required
from django.contrib.auth.models import User
from django.shortcuts import render, redirect
from sale.models import Sale
from user_profile.models import user_profile
from django.views import View
from product.models import Product
from sale.forms import frm_sell_products
from sale.models import Sale

## GoTos ##
##these functions send the user to some page##
@login_required
def manage_sales(request):
    return render(request, 'manage_sales.html',
                  {'sales': Sale.objects.all(), 'logged_profile': get_logged_user(request)})


@login_required
def show_sale(request, product_id):
    sale = Sale.objects.get(id=product_id)
    return render(request, 'show_sale.html', {'logged_profile': get_logged_user(request), 'sale': sale})


@login_required()
def remove_sale(request, sale_id):
    sale = Sale.objects.get(id=sale_id)
    sale.delete()
    return redirect('manage_sales')


class ViewSellProduct(View):
    template_name = 'sell_product.html'

    def get(self, request, *args, **kwargs):
        products = Product.objects.all()
        buyer = user_profile.objects.all()
        return render(request, self.template_name, {'products': products, 'buyers': buyer})

    def post(self, request, *args, **kwargs):
        form = frm_sell_products(request.POST)

        if form.is_valid():
            form_data = form.data

            buyerReceived = user_profile.objects.get(id=form_data['buyer_selected'])
            products_selected_ids = request.POST.getlist('products_selected')
            products_selected_list = []


            saleToSave = Sale(
                buyer=buyerReceived.user,
                date=form_data['date_of_sale']
            )

            saleToSave.save()

            for received_id in products_selected_ids:
                prod = Product.objects.get(id=received_id)
                saleToSave.productsBuyed.add(prod)



            return redirect('manage_sales')

        return render(request, self.template_name, {'form': form})


class ViewEditSale(View):
    template_name = 'edit_sale.html'

    def get(self, request, *args, **kwargs):
        sale_selected = Sale.objects.get(id=kwargs['sale_id'])
        return render(request, self.template_name, {'product': sale_selected})

    def post(self, request, *args, **kwargs):
        form = frm_sell_products(request.POST)

        if form.is_valid():
            form = frm_sell_products(request.POST)

            if form.is_valid():
                form_data = form.data

                buyerReceived = User.objects.get(id=form_data['buyer_selected'])
                productsBuyedReceived = None

                saleToSave = Sale(
                    id  = kwargs['sale_id'],
                    buyer=buyerReceived,
                    date=form_data['date_of_sale'],
                    productsBuyed=productsBuyedReceived
                )

                saleToSave.save()

                return redirect('manage_sales')

            return render(request, self.template_name, {'form': form})


##auxiliary functions
def get_logged_user(request):
    return request.user.user_profile
