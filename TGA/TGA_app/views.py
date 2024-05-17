from django.shortcuts import render
from .models import *
import json
from django.core import serializers
from django.http import HttpResponse, JsonResponse
from django.db.models import Q
from django.db.models import Count
import re
from django.views.decorators.cache import never_cache
from django.core.files.storage import FileSystemStorage
import random
from django.views.decorators.csrf import csrf_exempt
import os
from datetime import datetime
from datetime import date
from datetime import timedelta
import base64
import pandas as pd
from statistics import mean



# Create your views here.



@csrf_exempt
def register(request):
	name=request.POST.get("name")
	address=request.POST.get("address")
	mobile=request.POST.get("mobile")
	email=request.POST.get("email")
	username=request.POST.get("username")
	password=request.POST.get("password")

	print(name)
	print(address)

	response_data={}
	try:
		d = Users.objects.filter(username=username)
		c = d.count()
		if c == 1:
			response_data['msg'] = "Username Already Taken"
		else:
			ob=Users(name=name,address=address,mobile=mobile,email=email,username=username,password=password)
			ob.save()

			obj=Friends(username=username,friends_name='')
			obj.save()

			response_data['msg'] = "yes"
	except:
		response_data['msg'] = "no"
	return JsonResponse(response_data)

@csrf_exempt
def check_requests(request):

	username=request.POST.get("username")
	selected_username=request.POST.get("s_username")

	obj1=Friends.objects.get(username=username)
	friends_list=obj1.friends_name

	print("\n--------------------")
	print(friends_list)

	response_data={}

	get_friends_list=friends_list.split(',')
	print("Get friends list : ",get_friends_list)


	if selected_username not in get_friends_list:
		print("hai")

		query=Q(sender_username__icontains=selected_username) & Q(receiver_username__icontains=username) | \
		Q(sender_username__icontains=username) & Q(receiver_username__icontains=selected_username)


		blk1=FR_Table.objects.filter(query)

		c = blk1.count()

		if c!=0:
			response_data['msg'] = "Wait"
			response_data['unm'] = selected_username

		else:
			response_data['msg'] = "Not Exist"
			response_data['unm'] = selected_username

		# friends_list_=friends_list+","+selected_username
		# obj1.friends_name=friends_list_
	else:
		response_data['msg'] = "Exist"
		response_data['unm'] = selected_username

	return JsonResponse(response_data)

@csrf_exempt
def accept_request(request):
	r_id=request.POST.get("r_id")


	response_data={}
	obj1=FR_Table.objects.get(r_id=int(r_id))

	sender_name=obj1.sender_username
	receiver_name=obj1.receiver_username

	obj2=Friends.objects.get(username=receiver_name)
	friends_list=obj2.friends_name
	obj2.friends_name=friends_list+","+sender_name
	obj2.save()

	obj3=Friends.objects.get(username=sender_name)
	friends_list=obj3.friends_name
	obj3.friends_name=friends_list+","+receiver_name
	obj3.save()

	obj11=FR_Table.objects.get(r_id=int(r_id))
	obj11.delete()


	response_data["msg"]="yes"
	return JsonResponse(response_data,safe=False)



@csrf_exempt
def sent_req(request):
	sender_username=request.POST.get("sender_username")
	receiver_username=request.POST.get("receiver_username")

	obj1=FR_Table(sender_username=sender_username,receiver_username=receiver_username)
	obj1.save()

	data={"msg":"request sent"}
	return JsonResponse(data,safe=False)



@csrf_exempt
def find_login(request):
	username=request.POST.get("username")
	password=request.POST.get("password")

	try:
		ob=Users.objects.get(username=username,password=password)

		data={"msg":"User"}
		return JsonResponse(data,safe=False)
	except:
		data={"msg":"no"}
		return JsonResponse(data,safe=False)


@csrf_exempt
def get_my_requests(request):

	username=request.POST.get("username")
 
	resplist=[]
	respdata={}
	ob=FR_Table.objects.filter(receiver_username=username)
	
	resplist=[]
	respdata={}
	for i in ob:
		data={}
		data["r_id"]=i.r_id
		data["sender_username"]=i.sender_username
		data["receiver_username"]=i.receiver_username
  
		resplist.append(data)
	respdata["data"]=resplist
	print("respdata : ",respdata)
	return JsonResponse(respdata,safe=False)



@csrf_exempt
def get_registered_users(request):
 
	resplist=[]
	respdata={}
	ob=Users.objects.all()
	
	resplist=[]
	respdata={}
	for i in ob:
		data={}
		data["u_id"]=i.u_id
		data["name"]=i.name
		data["username"]=i.username
		data["address"]=i.address
		data["mobile"]=i.mobile
		data["email"]=i.email
  
		resplist.append(data)
	respdata["data"]=resplist
	print("respdata : ",respdata)
	return JsonResponse(respdata,safe=False)

@csrf_exempt
def Add_place(request):
	pname=request.POST.get("pname")
	ptype=request.POST.get("ptype")
	o_time=request.POST.get("o_time")
	c_time=request.POST.get("c_time")
	amount=request.POST.get("amount")
	latitude=request.POST.get("latitude")
	longitude=request.POST.get("longitude")
	link=request.POST.get("link")

	response_data={}
	try:
		d = Place.objects.filter(pname=pname)
		c = d.count()
		if c == 1:
			response_data['msg'] = "Already Exists"
		else:
			rating=""
			ob=Place(pname=pname,ptype=ptype,rating=rating,o_time=o_time,c_time=c_time,amount=amount,latitude=latitude,longitude=longitude,link=link)
			ob.save()

			response_data['msg'] = "yes"
	except:
		response_data['msg'] = "no"
	return JsonResponse(response_data)

@csrf_exempt
def delete(request):
	p_id=request.POST.get("p_id")


	response_data={}
	obj1=Place.objects.get(p_id=int(p_id))
	obj1.delete()

	response_data["msg"]="yes"
	return JsonResponse(response_data,safe=False)

@csrf_exempt
def reject_request(request):
	r_id=request.POST.get("r_id")


	response_data={}
	obj1=FR_Table.objects.get(r_id=int(r_id))
	obj1.delete()

	response_data["msg"]="yes"
	return JsonResponse(response_data,safe=False)


@csrf_exempt
def view_places_admin(request):
 
	resplist=[]
	respdata={}
	ob=Place.objects.all()
	
	resplist=[]
	respdata={}
	for i in ob:
		data={}
		data["p_id"]=i.p_id
		data["pname"]=i.pname
		data["ptype"]=i.ptype
		data["rating"]=i.rating
		data["o_time"]=i.o_time
		data["c_time"]=i.c_time
		data["amount"]=i.amount
		data["latitude"]=i.latitude
		data["longitude"]=i.longitude
		data["link"]=i.link
  
		resplist.append(data)
	respdata["data"]=resplist
	print("respdata : ",respdata)
	return JsonResponse(respdata,safe=False)

from math import cos, asin, sqrt

def distance(lat1, lon1, lat2, lon2):
	p = 0.017453292519943295
	hav = 0.5 - cos((lat2-lat1)*p)/2 + cos(lat1*p)*cos(lat2*p) * (1-cos((lon2-lon1)*p)) / 2
	return 12742 * asin(sqrt(hav))

def closest(data, v):
	return sorted(data, key=lambda p: distance(v['latitude'],v['longitude'],p['latitude'],p['longitude']))



@csrf_exempt
def Search_place(request):
	username=request.POST.get("username")
	ptype=request.POST.get("ptype")
	pacetype=request.POST.get("pacetype")
	latitude=request.POST.get("latitude")
	longitude=request.POST.get("longitude")

	if pacetype=="Low":
		pace_no=2

	if pacetype=="High":
		pace_no=4


	print(username,ptype,pacetype,latitude,longitude)

	my_dict={}
	my_dict['latitude']=float(latitude)
	my_dict['longitude']=float(longitude)

	print("\n my dict ....>")
	print(my_dict)


	resplist=[]
	respdata={}
	ob=Place.objects.filter(ptype=ptype)
	for i in ob:
	
		data={}
		data["p_id"]=i.p_id
		data["pname"]=i.pname
		data["rating"]=i.rating
		data["o_time"]=i.o_time
		data["c_time"]=i.c_time
		data["amount"]=i.amount
		data["link"]=i.link
		data["latitude"]=float(i.latitude)
		data["longitude"]=float(i.longitude)

		resplist.append(data)

	print('*****************************STEP 1')
	print(resplist)
	print("\n###################################")
	get_sorted=closest(resplist, my_dict)
	print(get_sorted)

	print("Pace Number : ",pace_no)

	if pace_no==2:
		based_on_pace=get_sorted[0:2]
	if pace_no==4:
		based_on_pace=get_sorted[0:4]

	print(based_on_pace)

	respdata["data"]=based_on_pace
	respdata["my_lat"]=latitude
	respdata["my_long"]=longitude
	print("respdata : ",respdata)
	return JsonResponse(respdata,safe=False)


@csrf_exempt
def Upload_Excel(request):
	uname=request.POST.get("uname")
	fpath=request.POST.get("fpath")
	encodedstr=request.POST.get("encodedstr")
	print("path==>",fpath)
	flist=fpath.split("/")
	fname=flist[-1]
	print("filename==>",fname)
	print("length",len(encodedstr))

	base64_img_bytes = encodedstr.encode('utf-8')
	with open('TGA_app/static/Excel_Files/'+fname, 'wb') as file_to_save:
		decoded_image_data = base64.decodebytes(base64_img_bytes)
		file_to_save.write(decoded_image_data)

	dataframe1 = pd.read_excel('TGA_app/static/Excel_Files/'+fname)
	print(dataframe1)
	print(dataframe1.shape)
	length=dataframe1.shape[0]
	print(length)

	response_data={}
	for i in range(length):
		print(dataframe1.iloc[i].tolist())
		get_list=dataframe1.iloc[i].tolist()

		pname=get_list[1]
		ptype = get_list[2]
		rating = get_list[3]
		o_time=get_list[4]
		c_time=get_list[5]
		latitude=get_list[6]
		longitude=get_list[7]
		amount = get_list[8]
		link = get_list[9]
	

		d = Place.objects.filter(pname=pname)
		c = d.count()
		if c == 1:
			response_data['msg'] = "Already Exists"
			break
		else:
			ob=Place(pname=pname,ptype=ptype,rating=rating,o_time=o_time,c_time=c_time,amount=amount,latitude=latitude,longitude=longitude,link=link)
			ob.save()

			response_data['msg'] = "yes"

	return JsonResponse(response_data)


def calculate():
	df = pd.DataFrame(list(Rating.objects.all().values()))
	print(df)
	df.drop(['r_id', 'username'], axis=1,inplace=True)
	print(df)

	unq=df['pname'].unique()
	print(unq)

	for i in unq:
		# print(i)

		df1=df.loc[df['pname'] == i, 'rating_val'].values

		df1=df1.tolist() #convert to list

		# print(df1)
		df2=[float(x) for x in df1]

		mean_val=mean(df2)
		mean_val=round(mean_val, 1)

		print(i+" mean_val : "+str(mean_val))

		obj1=Place.objects.get(pname=i)
		obj1.rating=mean_val
		obj1.save()


@csrf_exempt
def Give_rating_one(request):
	username=request.POST.get("username")
	place1=request.POST.get("place1")
	place2=request.POST.get("place2")
	rating1=request.POST.get("rating1")
	rating2=request.POST.get("rating2")


	response_data={}
	for i in range(2):

		if i==0:
			d = Rating.objects.filter(username=username,pname=place1)
			c = d.count()
			if c == 1:
				pass
			else:
				ob=Rating(username=username,pname=place1,rating_val=rating1)
				ob.save()
		if i==1:
			d = Rating.objects.filter(username=username,pname=place2)
			c = d.count()
			if c == 1:
				pass
			else:
				ob1=Rating(username=username,pname=place2,rating_val=rating2)
				ob1.save()

	calculate()

	response_data['msg'] = "yes"
	return JsonResponse(response_data)




################

def calculate_two():
	df = pd.DataFrame(list(Rating.objects.all().values()))
	print(df)
	df.drop(['r_id', 'username'], axis=1,inplace=True)
	print(df)

	unq=df['pname'].unique()
	print(unq)

	for i in unq:
		# print(i)

		df1=df.loc[df['pname'] == i, 'rating_val'].values

		df1=df1.tolist() #convert to list

		# print(df1)
		df2=[float(x) for x in df1]

		mean_val=mean(df2)
		mean_val=round(mean_val, 1)

		print(i+" mean_val : "+str(mean_val))

		obj1=Place.objects.get(pname=i)
		obj1.rating=mean_val
		obj1.save()

@csrf_exempt
def Give_rating_two(request):
	username=request.POST.get("username")
	place1=request.POST.get("place1")
	place2=request.POST.get("place2")
	place3=request.POST.get("place3")
	place4=request.POST.get("place4")
	rating1=request.POST.get("rating1")
	rating2=request.POST.get("rating2")
	rating3=request.POST.get("rating3")
	rating4=request.POST.get("rating4")


	response_data={}
	for i in range(4):

		if i==0:
			d = Rating.objects.filter(username=username,pname=place1)
			c = d.count()
			if c == 1:
				pass
			else:
				ob=Rating(username=username,pname=place1,rating_val=rating1)
				ob.save()
		if i==1:
			d = Rating.objects.filter(username=username,pname=place2)
			c = d.count()
			if c == 1:
				pass
			else:
				ob1=Rating(username=username,pname=place2,rating_val=rating2)
				ob1.save()

		if i==2:
			d = Rating.objects.filter(username=username,pname=place3)
			c = d.count()
			if c == 1:
				pass
			else:
				ob1=Rating(username=username,pname=place3,rating_val=rating3)
				ob1.save()

		if i==3:
			d = Rating.objects.filter(username=username,pname=place4)
			c = d.count()
			if c == 1:
				pass
			else:
				ob1=Rating(username=username,pname=place4,rating_val=rating4)
				ob1.save()

	calculate_two()

	response_data['msg'] = "yes"
	return JsonResponse(response_data)


@csrf_exempt
def users_list(request):
	username=request.POST.get("username")

	resplist=[]
	respdata={}
	ob=Users.objects.exclude(username=username)

	resplist=[]
	respdata={}
	for i in ob:
		data={}
		data["username"]=i.username

		resplist.append(data)
	respdata["data"]=resplist
	print("respdata : ",respdata)
	return JsonResponse(respdata,safe=False)

@csrf_exempt
def filter_(request):
	sender=request.POST.get("sender")
	receiver=request.POST.get("receiver")

	resplist=[]
	respdata={}
	ob=Chat.objects.filter(Q(sender=sender,receiver=receiver) | Q(sender=receiver,receiver=sender))#sender=sender,receiver=receiver)

	resplist=[]
	respdata={}
	for i in ob:
		data={}
		data["sender"]=i.sender
		data["message"]=i.message

		resplist.append(data)
	respdata["data"]=resplist
	print("respdata : ",respdata)
	return JsonResponse(respdata,safe=False)



@csrf_exempt
def send_message(request):
	print("***********   HAI ")
	sender=request.POST.get("sender")
	receiver=request.POST.get("receiver")
	message=request.POST.get("message")
	datetime=request.POST.get("date_time")

	print(sender,receiver,datetime,message)
	response_data={}
	try:
		print("TRY")
		ob=Chat(sender=sender,receiver=receiver,message=message,datetime=datetime)
		ob.save()

		response_data['msg'] = "yes"
	except:
		print("EXCEPT")
		response_data['msg'] = "no"
	return JsonResponse(response_data)