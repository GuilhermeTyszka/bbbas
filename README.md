import requests
import json

# Your incoming webhook URL
webhook_url = 'YOUR_WEBHOOK_URL'

# Define the message payload
message = {
    'text': 'Hello from Python!',
    'title': 'Python Bot'
}

# Send the message to Microsoft Teams
response = requests.post(
    webhook_url,
    data=json.dumps(message),
    headers={'Content-Type': 'application/json'}
)

if response.status_code == 200:
    print('Message sent successfully!')
else:
    print('Failed to send message:', response.status_code)
