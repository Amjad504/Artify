

import numpy as np
import cv2
from PIL import Image
from PIL import Image, ImageEnhance, ImageFilter
import base64
import io



# Function to decode an image from base64 format
def decode_image(image):
    decoded_data = base64.b64decode(image)  # Decoding base64 data
    np_data = np.frombuffer(decoded_data, np.uint8)  # Converting decoded data to numpy array of unsigned integers
    img = cv2.imdecode(np_data, cv2.IMREAD_UNCHANGED)  # Decoding numpy array as an image using OpenCV
    return img

# Function to convert an image to base64 string format
def convert_to_string(image):
    pil_im = Image.fromarray(image)  # Creating a PIL image from the input image array
    buff = io.BytesIO()  # Creating a byte buffer
    pil_im.save(buff, format="PNG")  # Saving the PIL image to the buffer in PNG format
    img_str = base64.b64encode(buff.getvalue())  # Encoding the buffer content to base64 string
    return "" + str(img_str, 'utf-8')  # Converting the base64 bytes to string format

# Function to convert an image to grayscale
def gray_scale(image):
    img = decode_image(image)  # Decoding the input image
    img_gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)  # Converting the image to grayscale using OpenCV

    img_str = convert_to_string(img_gray)  # Converting the grayscale image to base64 string format
    return img_str

# Function to apply a sepia filter to an image
def apply_sepia(image):
    img = decode_image(image)  # Decoding the input image
    img_gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)  # Converting the image to grayscale using OpenCV

    gray_3_channels = cv2.cvtColor(img_gray, cv2.COLOR_GRAY2BGR)  # Converting the grayscale image to 3-channel grayscale

    sepia = np.array([[0.272, 0.534, 0.131],  # Sepia transformation matrix
                      [0.349, 0.686, 0.168],
                      [0.393, 0.769, 0.189]])

    sepia_image = cv2.transform(gray_3_channels, sepia)  # Applying sepia transformation to the 3-channel grayscale image

    img_str = convert_to_string(sepia_image)  # Converting the sepia image to base64 string format
    return img_str



# Function to apply black and white filter to an image
def apply_black_and_white(image):
    img = decode_image(image)  # Decoding the input image
    img_gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)  # Converting the image to grayscale using OpenCV

    _, black_white_image = cv2.threshold(img_gray, 127, 255, cv2.THRESH_BINARY)  # Applying black and white filter using a threshold value

    black_white_image = cv2.cvtColor(black_white_image, cv2.COLOR_GRAY2BGR)  # Converting the black and white image to BGR color space

    img_str = convert_to_string(black_white_image)  # Converting the black and white image to base64 string format
    return img_str

# Function to apply vintage filter to an image
def apply_vintage(image):
    img = decode_image(image)  # Decoding the input image

    vintage_image = cv2.applyColorMap(img, cv2.COLORMAP_AUTUMN)  # Applying vintage filter using OpenCV's applyColorMap function

    img_str = convert_to_string(vintage_image)  # Converting the vintage image to base64 string format
    return img_str

# Function to adjust the brightness of an image
def adjust_brightness(image, factor):
    if factor == 0:
        return image

    img = decode_image(image)  # Decoding the input image
    img2 = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)  # Converting the BGR image to RGB

    img_pil = Image.fromarray(img2)  # Creating a PIL image from the RGB image

    if img_pil.mode == "L":
        img_pil = img_pil.convert("RGB")  # Converting the image to RGB if it's grayscale

    enhancer = ImageEnhance.Brightness(img_pil)  # Creating an enhancer object for adjusting brightness

    brightness_factor = 1 + (factor / 100.0)  # Calculating the brightness factor

    adjusted_image = enhancer.enhance(brightness_factor)  # Adjusting the brightness of the image

    buff = io.BytesIO()
    adjusted_image.save(buff, format="PNG")  # Saving the adjusted image to the buffer in PNG format
    img_str = base64.b64encode(buff.getvalue()).decode("utf-8")  # Converting the adjusted image to base64 string format
    return img_str

# Function to adjust the contrast of an image
def adjust_contrast(image, factor):
    if factor == 0:
        return image

    img = decode_image(image)  # Decoding the input image
    img2 = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)  # Converting the BGR image to RGB

    img_pil = Image.fromarray(img2)  # Creating a PIL image from the RGB image

    if img_pil.mode == "L":
        img_pil = img_pil.convert("RGB")  # Converting the image to RGB if it's grayscale

    enhancer = ImageEnhance.Contrast(img_pil)  # Creating an enhancer object for adjusting contrast

    contrast_factor = 1 + (factor / 100.0)  # Calculating the contrast factor

    adjusted_image = enhancer.enhance(contrast_factor)  # Adjusting the contrast of the image

    buff = io.BytesIO()
    adjusted_image.save(buff, format="PNG")  # Saving the adjusted image to the buffer in PNG format
    img_str = base64.b64encode(buff.getvalue()).decode("utf-8")  # Converting the adjusted image to base64 string format
    return img_str

# Function to adjust the saturation of an image
def adjust_saturation(image, factor):
    if factor == 0:
        return image

    img = decode_image(image)  # Decoding the input image
    img2 = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)  # Converting the BGR image to RGB

    img_pil = Image.fromarray(img2)  # Creating a PIL image from the RGB image

    if img_pil.mode == "L":
        img_pil = img_pil.convert("RGB")  # Converting the image to RGB if it's grayscale

    enhancer = ImageEnhance.Color(img_pil)  # Creating an enhancer object for adjusting saturation

    saturation_factor = 1 + (factor / 100.0)  # Calculating the saturation factor

    adjusted_image = enhancer.enhance(saturation_factor)  # Adjusting the saturation of the image

    buff = io.BytesIO()
    adjusted_image.save(buff, format="PNG")  # Saving the adjusted image to the buffer in PNG format
    img_str = base64.b64encode(buff.getvalue()).decode("utf-8")  # Converting the adjusted image to base64 string format
    return img_str

# Function to adjust the sharpening of an image
def adjust_sharpening(image, factor):
    if factor == 0:
        return image

    img = decode_image(image)  # Decoding the input image
    img2 = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)  # Converting the BGR image to RGB

    img_pil = Image.fromarray(img2)  # Creating a PIL image from the RGB image

    if img_pil.mode == "L":
        img_pil = img_pil.convert("RGB")  # Converting the image to RGB if it's grayscale

    # Apply a sharpening filter to enhance the image details
    sharpened_img = img_pil.filter(ImageFilter.UnsharpMask(radius=2, percent=factor))

    buff = io.BytesIO()
    sharpened_img.save(buff, format="PNG")  # Saving the sharpened image to the buffer in PNG format
    img_str = base64.b64encode(buff.getvalue()).decode("utf-8")  # Converting the sharpened image to base64 string format

    return img_str



# Function to cartoonify an image
def cartoonify_image(image):
    img = decode_image(image)  # Decoding the input image

    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)  # Convert the image to grayscale

    gray = cv2.medianBlur(gray, 5)  # Apply median blur to reduce noise

    edges = cv2.adaptiveThreshold(gray, 255, cv2.ADAPTIVE_THRESH_MEAN_C, cv2.THRESH_BINARY, 9, 9)  # Perform adaptive thresholding to obtain edges

    color = cv2.bilateralFilter(img, 9, 250, 250)  # Create a color image from the edges
    cartoon = cv2.bitwise_and(color, color, mask=edges)  # Apply the edges as a mask to the color image

    newImg = cv2.cvtColor(cartoon, cv2.COLOR_BGR2RGB)  # Convert the image to RGB format

    img_str = convert_to_string(newImg)  # Convert the cartoonified image to base64 string format
    return img_str


# Function to create a pencil sketch of an image
def sketch_image(image):
    img = decode_image(image)  # Decoding the input image

    gray_image = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)  # Convert the image to grayscale

    inverted_image = cv2.bitwise_not(gray_image)  # Invert the grayscale image

    blurred_image = cv2.GaussianBlur(inverted_image, (21, 21), 0)  # Blur the inverted image using Gaussian blur

    inverted_blurred_image = cv2.bitwise_not(blurred_image)  # Invert the blurred image

    pencil_sketch_image = cv2.divide(gray_image, inverted_blurred_image, scale=256.0)  # Create the pencil sketch image

    img_str = convert_to_string(pencil_sketch_image)  # Convert the pencil sketch image to base64 string format
    return img_str


# Function to remove the background of an image
def remove_background(image):
    img = decode_image(image)  # Decoding the input image

    gray_image = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)  # Convert the image to grayscale

    _, thresholded_image = cv2.threshold(gray_image, 1, 255, cv2.THRESH_BINARY)  # Apply thresholding to create a binary image

    contours, _ = cv2.findContours(thresholded_image, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)  # Find contours in the binary image

    mask = np.zeros_like(gray_image)  # Create a mask with white pixels on a black background
    cv2.drawContours(mask, contours, -1, (255), thickness=cv2.FILLED)  # Draw contours on the mask

    masked_image = cv2.bitwise_and(img, img, mask=mask)  # Apply the mask to the original image

    img_str = convert_to_string(masked_image)  # Convert the image with removed background to base64 string format
    return img_str

