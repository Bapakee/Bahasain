// Utility function for creating a standard API response
const successResponse = (res, data, message = 'Success', statusCode = 200) => {
  // Jika data kosong, ubah pesan menjadi "Data is empty"
  if (Array.isArray(data) && data.length === 0) {
      message = 'Data is empty';
  }

  // Fungsi untuk menghapus properti yang mengandung "_"
  const cleanData = Array.isArray(data)
      ? data.map(item => {
          return Object.fromEntries(
              Object.entries(item).filter(([key]) => !key.includes('_'))
          );
      })
      : Object.fromEntries(
          Object.entries(data).filter(([key]) => !key.includes('_'))
      );

  return res.status(statusCode).json({
      success: true,
      message,
      data: cleanData
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
  