
#!/usr/bin/python
## get subprocess module 
import subprocess

import sys
 
## call date command ##
genderCommand  = 'python guess.py --class_type gender --model_type inception --model_dir ../_checkpoints/21936/ --face_detection_type dlib --face_detection_model shape_predictor_68_face_landmarks.dat                --filename ../_image_data/IMG_2593.png'
genderCommand2 = 'python guess.py --class_type gender --model_type inception --model_dir ../_checkpoints/21936/                            --face_detection_model ../darkflow/haar/haarcascade_frontalface_default.xml --filename ../_image_data/IMG_2593.png'

genderCommand3 = 'python guess.py --class_type gender --model_type inception --model_dir ../_checkpoints/21936/ --face_detection_type dlib --face_detection_model shape_predictor_68_face_landmarks.dat                --filename ' + sys.argv[1]
genderCommand4 = 'python guess.py --class_type gender --model_type inception --model_dir ../_checkpoints/21936/                            --face_detection_model ../darkflow/haar/haarcascade_frontalface_default.xml --filename ' + sys.argv[1]

relativePath = sys.argv[2]

genderCommand5 = 'python '+relativePath+'guess.py --class_type gender --model_type inception --model_dir '+relativePath+'../_checkpoints/21936/ --face_detection_type dlib --face_detection_model '+relativePath+'shape_predictor_68_face_landmarks.dat                --filename ' + sys.argv[1]
genderCommand6 = 'python '+relativePath+'guess.py --class_type gender --model_type inception --model_dir '+relativePath+'../_checkpoints/21936/                            --face_detection_model '+relativePath+'../darkflow/haar/haarcascade_frontalface_default.xml --filename ' + sys.argv[1]

p = subprocess.Popen(genderCommand6, stdout=subprocess.PIPE, shell=True)
 
## Talk with date command i.e. read data from stdout and stderr. Store this info in tuple ##
## Interact with process: Send data to stdin. Read data from stdout and stderr, until end-of-file is reached.  ##
## Wait for process to terminate. The optional input argument should be a string to be sent to the child process, ##
## or None, if no data should be sent to the child.
(output, err) = p.communicate()

predictedGender = '' 
prefix = 'Guess @ 1 '
lines = output.splitlines()
for lineNum in range(len(lines)):
  line = str(lines[lineNum])
  #print("debug - "+line)
  if prefix in line:
    #print("debug - " + line)
    commaIndex = line.find(',')
    #print("debug - " + commaIndex)
    predictedGender = line[commaIndex-1:commaIndex]
    #print("debug - predicted gender is " + predictedGender)
    print(predictedGender)
  

## Wait for date to terminate. Get return returncode ##
p_status = p.wait()
#print("debug - Command output : "+str(output))
#print("debug - Command exit status/return code : "+str(p_status))




'''
  769  python guess.py --class_type age --model_type inception --model_dir ../_checkpoints/22801/ --filename ../_image_data/IMG_2578.png
  770  mv ~/Downloads/IMG_2593.png ../_image_data/
  771  python guess.py --class_type age --model_type inception --model_dir ../_checkpoints/22801/ --filename ../_image_data/IMG_2593.png
  772  python guess.py --class_type age --model_type inception --model_dir ../_checkpoints/22801/ --filename ../_image_data/IMG_2593.png --face_detection_mode ../darkflow/haar/haarcascade_frontalface_default.xml
  773  python guess.py --class_type gender --model_type inception --model_dir ../_checkpoints/21936/ --filename ../_image_data/IMG_2593.png --face_detection_mode ../darkflow/haar/haarcascade_frontalface_default.xml
  774  python guess.py --class_type gender --model_type inception --model_dir ../_checkpoints/21936/ --filename ../_image_data/IMG_2593.png
  775  wget http://dlib.net/files/shape_predictor_68_face_landmarks.dat.bz2
  776  brew install wget
  777  wget http://dlib.net/files/shape_predictor_68_face_landmarks.dat.bz2
  778  bunzip2
  779  bunzip2 bunzip2 shape_predictor_68_face_landmarks.dat.bz2
  780  bunzip2 shape_predictor_68_face_landmarks.dat.bz2
  781  ls
  782  pip install dlib
  783  python guess.py --class_type gender --model_type inception --model_dir ../_checkpoints/21936/ --filename ../_image_data/IMG_2593.png --face_detection_type dlib --face_detection_model shape_predictor_68_face_landmarks.dat
  784  python guess.py --class_type age --model_type inception --model_dir ../_checkpoints/22801/ --filename ../_image_data/IMG_2593.png --face_detection_type dlib --face_detection_model shape_predictor_68_face_landmarks.dat
  785  git push origin3
  786  python guess.py --class_type age --model_type inception --model_dir ../_checkpoints/22801/ --filename ../_image_data/IMG_2593.png --face_detection_type dlib --face_detection_model shape_predictor_68_face_landmarks.dat
  787  git status
  788  python guess.py --class_type gender --model_type inception --model_dir ../_checkpoints/21936/ --filename ../_image_data/IMG_2593.png --face_detection_type dlib --face_detection_model shape_predictor_68_face_landmarks.dat
'''
