// Initialize databases for loan request services
db = db.getSiblingDB('loan_request_command');
db.createCollection('loan_requests');

db = db.getSiblingDB('loan_request_query');
db.createCollection('loan_request_views');

print('Loan request databases initialized successfully');