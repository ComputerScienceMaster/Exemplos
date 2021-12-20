# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.contrib.auth.decorators import login_required
from django.contrib.auth.models import User
from django.shortcuts import render, redirect
from django.views import View

from product.forms import frm_add_product
from product.models import Product
from product.models import Category
from user_profile.models import user_profile


## GoTos ##
##these functions send the user to some page##
@login_required
def add_product(request):
    return render(request, 'add_product.html', {'logged_profile': get_logged_user(request)})


@login_required
def manage_products(request):
    return render(request, 'manage_products.html',
                  {'products': Product.objects.all(), 'logged_profile': get_logged_user(request)})


@login_required
def show_product(request, product_id):
    product = Product.objects.get(id=product_id)
    return render(request, 'show_product.html', {'logged_profile': get_logged_user(request), 'product': product})


##forms##

class ViewAddProduct(View):
    template_name = 'add_product.html'

    def get(self, request, *args, **kwargs):
        categories = Category.objects.all()
        return render(request, self.template_name, {'categories': categories})

    def post(self, request, *args, **kwargs):
        form = frm_add_product(request.POST)

        if form.is_valid():
            dados_form = form.data

            category = Category.objects.get(id=dados_form['category_selected'])

            product = Product(
                name=dados_form['name'],
                expiration=dados_form['expiration'],
                price=dados_form['price'],
                category=category
            )

            product.save()

            return redirect('manageproduct')

        return render(request, self.template_name, {'form': form})


class ViewEditProduct(View):
    template_name = 'edit_product.html'

    def get(self, request, *args, **kwargs):
        categories = Category.objects.all()
        product = Product.objects.get(id=kwargs['product_id'])
        return render(request, self.template_name, {'categories': categories, 'product': product})

    def post(self, request, *args, **kwargs):
        form = frm_add_product(request.POST)

        if form.is_valid():
            dados_form = form.data

            category = Category.objects.get(id=dados_form['category_selected'])

            product = Product(
                id=kwargs['product_id'],
                name=dados_form['name'],
                expiration=dados_form['expiration'],
                price=dados_form['price'],
                category=category
            )

            product.save()

            return redirect('manageproduct')

        return render(request, self.template_name, {'form': form})


@login_required()
def remove_product(request, product_id):
    product = Product.objects.get(id=product_id)
    product.delete()
    return redirect('manageproduct')


##auxiliary functions
def get_logged_user(request):
    return request.user.user_profile
