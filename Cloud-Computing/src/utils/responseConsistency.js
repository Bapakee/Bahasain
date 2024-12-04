// Utility function for creating a standard API response
const successResponse = (res, data, message = 'Success', statusCode = 200) => {
    return res.status(statusCode).json({
      success: true,
      message,
      data
    });
  };
  
  // Utility function for creating a standard error response
  const errorResponse = (res, error, message = 'Something went wrong', statusCode = 500) => {
    return res.status(statusCode).json({
      success: false,
      message,
      error: error instanceof Error ? error.message : error
    });
  };
  
  // Utility function for paginated responses
  const paginatedResponse = (res, data, message = 'Success', page, limit, total) => {
    return res.status(200).json({
      success: true,
      message:message,
      data,
      pagination: {
        page:parseInt(page),
        limit:parseInt(limit),
        total:parseInt(total)
      }
    });
  };
  
  module.exports = { successResponse, errorResponse, paginatedResponse };
  