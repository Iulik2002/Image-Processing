Functionality
1. Image Representation
Images are represented as lists of lists of pixels.
Each pixel contains red, green, and blue components.
2. Input/Output
fromStringPPM(image: List[Char]): Image: Converts a PPM format string to an image.
toStringPPM(image: Image): List[Char]: Converts an image to a PPM format string.
3. Concatenation
verticalConcat(image1: Image, image2: Image): Image: Concatenates two images vertically.
horizontalConcat(image1: Image, image2: Image): Image: Concatenates two images horizontally.
4. Rotation
rotate(image: Image, degrees: Integer): Image: Rotates an image by the specified angle (90, 180, 270 degrees).
5. Convolution
applyConvolution(image: GrayscaleImage, kernel: GrayscaleImage): GrayscaleImage: Applies convolution on a grayscale image using the given kernel.
6. Edge Detection
edgeDetection(image: Image, threshold: Double): Image: Detects edges in an image using the Sobel operator and a specified threshold.
7. Pascal's Triangle
pascalTriangle(n: Int): List[List[BigInt]]: Generates Pascal's Triangle up to the specified level.
moduloPascal(m: Integer, funct: Integer => Pixel, size: Integer): Image: Generates Pascal's Triangle with elements modulo m.
Implementation
The library is implemented in Scala.
It utilizes functional programming concepts for clarity and conciseness.
Image processing operations are performed using nested lists and mathematical computations.
